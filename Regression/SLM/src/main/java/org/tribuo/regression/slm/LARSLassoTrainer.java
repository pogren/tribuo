/*
 * Copyright (c) 2015, 2022, Oracle and/or its affiliates. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.tribuo.regression.slm;

import com.oracle.labs.mlrg.olcut.util.Pair;
import org.tribuo.math.la.DenseMatrix;
import org.tribuo.math.la.DenseVector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

/**
 * A trainer for a lasso linear regression model which uses LARS to construct the model.
 * Each output dimension is trained independently.
 * <p>
 * See:
 * <pre>
 * Efron B, Hastie T, Johnstone I, Tibshirani R.
 * "Least Angle Regression"
 * The Annals of Statistics, 2004.
 * </pre>
 */
public class LARSLassoTrainer extends SLMTrainer {
    private static final Logger logger = Logger.getLogger(LARSLassoTrainer.class.getName());

    /**
     * Constructs a lasso LARS trainer for a linear model.
     * @param maxNumFeatures The maximum number of features to select. Supply -1 to select all features.
     */
    public LARSLassoTrainer(int maxNumFeatures) {
        super(true,maxNumFeatures);
    }

    /**
     * Constructs a lasso LARS trainer that selects all the features.
     */
    public LARSLassoTrainer() {
        this(-1);
    }

    @Override
    protected DenseVector newWeights(SLMState state) {
        if (state.last) {
            return super.newWeights(state);
        }

        Pair<DenseVector, DenseMatrix> deltapi =  SLMTrainer.ordinaryLeastSquares(state.xpi,state.r);

        if (deltapi == null) {
            return null;
        }

        DenseVector delta = state.unpack(deltapi.getA());
        DenseMatrix xpiInv = deltapi.getB();

        // Computing gamma
        List<Double> candidates = new ArrayList<>();

        double AA = xpiInv.rowSum().sum();
        double CC = state.C;

        DenseVector wa = SLMTrainer.getWA(xpiInv,AA);
        DenseVector ar = SLMTrainer.getA(state.X, state.xpi, wa);

        for (int i = 0; i < state.numFeatures; ++i) {
            if (!state.activeSet.contains(i)) {
                double c = state.corr.get(i);
                double a = ar.get(i);

                double v1 = (CC - c) / (AA - a);
                double v2 = (CC + c) / (AA + a);

                if (v1 >= 0) {
                    candidates.add(v1);
                }
                if (v2 >= 0) {
                    candidates.add(v2);
                }
            }
        }

        double gamma = Collections.min(candidates);

        delta.scaleInPlace(gamma);

        for (int i = 0; i < state.numFeatures; ++i) {
            double betaElement = state.beta.get(i);
            double otherElement = delta.get(i);
            if ((betaElement > 0 && betaElement + otherElement < 0)
                    || (betaElement < 0 && betaElement + otherElement > 0)) {
                state.beta.set(i,0.0);
                delta.set(i,0.0);
                Integer integer = i;
                state.active.remove(integer);
                state.activeSet.remove(integer);
            }
        }

        return state.beta.add(delta);
    }

    @Override
    public String toString() {
        return "LARSLassoTrainer(maxNumFeatures="+maxNumFeatures+")";
    }
}

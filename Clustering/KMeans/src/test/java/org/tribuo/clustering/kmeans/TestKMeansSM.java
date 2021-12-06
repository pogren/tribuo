/*
 * Copyright (c) 2015-2020, Oracle and/or its affiliates. All rights reserved.
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

package org.tribuo.clustering.kmeans;

import org.tribuo.Dataset;
import org.tribuo.clustering.ClusterID;
import org.tribuo.clustering.evaluation.ClusteringEvaluation;
import org.tribuo.clustering.evaluation.ClusteringEvaluator;
import org.tribuo.clustering.example.ClusteringDataGenerator;
import org.tribuo.clustering.kmeans.KMeansTrainer.Distance;

import java.lang.reflect.InvocationTargetException;

/**
 * Smoke tests for k-means.
 */
public class TestKMeansSM {
    private static final KMeansTrainer trainer = new KMeansTrainer(5,10,Distance.EUCLIDEAN,1,1);
    private static final KMeansTrainer parTrainer = new KMeansTrainer(5,10,Distance.EUCLIDEAN,4,1);

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> clazz = Class.forName("org.opensearch.secure_sm.SecureSM");
        System.setSecurityManager((SecurityManager)clazz.getConstructor().newInstance());
        Dataset<ClusterID> data = ClusteringDataGenerator.gaussianClusters(500, 1L);
        Dataset<ClusterID> test = ClusteringDataGenerator.gaussianClusters(500, 2L);
        ClusteringEvaluator eval = new ClusteringEvaluator();

        KMeansModel model = trainer.train(data);

        ClusteringEvaluation testEvaluation = eval.evaluate(model,test);

        System.out.println("Sequential evaluation complete: " + testEvaluation.toString());

        KMeansModel parModel = parTrainer.train(data);

        ClusteringEvaluation parTestEvaluation = eval.evaluate(parModel,test);

        System.out.println("Parallel evaluation complete: " + parTestEvaluation.toString());
    }

}

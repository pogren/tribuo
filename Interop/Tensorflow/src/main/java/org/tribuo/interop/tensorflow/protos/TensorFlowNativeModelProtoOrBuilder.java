// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: tribuo-tensorflow.proto

package org.tribuo.interop.tensorflow.protos;

public interface TensorFlowNativeModelProtoOrBuilder extends
    // @@protoc_insertion_point(interface_extends:tribuo.interop.tensorflow.TensorFlowNativeModelProto)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>.tribuo.core.ModelDataProto metadata = 1;</code>
   * @return Whether the metadata field is set.
   */
  boolean hasMetadata();
  /**
   * <code>.tribuo.core.ModelDataProto metadata = 1;</code>
   * @return The metadata.
   */
  org.tribuo.protos.core.ModelDataProto getMetadata();
  /**
   * <code>.tribuo.core.ModelDataProto metadata = 1;</code>
   */
  org.tribuo.protos.core.ModelDataProtoOrBuilder getMetadataOrBuilder();

  /**
   * <code>bytes model_def = 2;</code>
   * @return The modelDef.
   */
  com.google.protobuf.ByteString getModelDef();

  /**
   * <code>map&lt;string, .tribuo.interop.tensorflow.TensorTupleProto&gt; tensors = 3;</code>
   */
  int getTensorsCount();
  /**
   * <code>map&lt;string, .tribuo.interop.tensorflow.TensorTupleProto&gt; tensors = 3;</code>
   */
  boolean containsTensors(
      java.lang.String key);
  /**
   * Use {@link #getTensorsMap()} instead.
   */
  @java.lang.Deprecated
  java.util.Map<java.lang.String, org.tribuo.interop.tensorflow.protos.TensorTupleProto>
  getTensors();
  /**
   * <code>map&lt;string, .tribuo.interop.tensorflow.TensorTupleProto&gt; tensors = 3;</code>
   */
  java.util.Map<java.lang.String, org.tribuo.interop.tensorflow.protos.TensorTupleProto>
  getTensorsMap();
  /**
   * <code>map&lt;string, .tribuo.interop.tensorflow.TensorTupleProto&gt; tensors = 3;</code>
   */

  org.tribuo.interop.tensorflow.protos.TensorTupleProto getTensorsOrDefault(
      java.lang.String key,
      org.tribuo.interop.tensorflow.protos.TensorTupleProto defaultValue);
  /**
   * <code>map&lt;string, .tribuo.interop.tensorflow.TensorTupleProto&gt; tensors = 3;</code>
   */

  org.tribuo.interop.tensorflow.protos.TensorTupleProto getTensorsOrThrow(
      java.lang.String key);

  /**
   * <code>.tribuo.interop.tensorflow.FeatureConverterProto feature_converter = 4;</code>
   * @return Whether the featureConverter field is set.
   */
  boolean hasFeatureConverter();
  /**
   * <code>.tribuo.interop.tensorflow.FeatureConverterProto feature_converter = 4;</code>
   * @return The featureConverter.
   */
  org.tribuo.interop.tensorflow.protos.FeatureConverterProto getFeatureConverter();
  /**
   * <code>.tribuo.interop.tensorflow.FeatureConverterProto feature_converter = 4;</code>
   */
  org.tribuo.interop.tensorflow.protos.FeatureConverterProtoOrBuilder getFeatureConverterOrBuilder();

  /**
   * <code>.tribuo.interop.tensorflow.OutputConverterProto output_converter = 5;</code>
   * @return Whether the outputConverter field is set.
   */
  boolean hasOutputConverter();
  /**
   * <code>.tribuo.interop.tensorflow.OutputConverterProto output_converter = 5;</code>
   * @return The outputConverter.
   */
  org.tribuo.interop.tensorflow.protos.OutputConverterProto getOutputConverter();
  /**
   * <code>.tribuo.interop.tensorflow.OutputConverterProto output_converter = 5;</code>
   */
  org.tribuo.interop.tensorflow.protos.OutputConverterProtoOrBuilder getOutputConverterOrBuilder();

  /**
   * <code>int32 batch_size = 6;</code>
   * @return The batchSize.
   */
  int getBatchSize();

  /**
   * <code>string output_name = 7;</code>
   * @return The outputName.
   */
  java.lang.String getOutputName();
  /**
   * <code>string output_name = 7;</code>
   * @return The bytes for outputName.
   */
  com.google.protobuf.ByteString
      getOutputNameBytes();
}

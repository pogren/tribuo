// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: tribuo-tensorflow.proto

package org.tribuo.interop.tensorflow.protos;

/**
 * <pre>
 *ImageConverter proto
 * </pre>
 *
 * Protobuf type {@code tribuo.interop.tensorflow.ImageConverterProto}
 */
public final class ImageConverterProto extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:tribuo.interop.tensorflow.ImageConverterProto)
    ImageConverterProtoOrBuilder {
private static final long serialVersionUID = 0L;
  // Use ImageConverterProto.newBuilder() to construct.
  private ImageConverterProto(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private ImageConverterProto() {
    inputName_ = "";
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new ImageConverterProto();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private ImageConverterProto(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new java.lang.NullPointerException();
    }
    com.google.protobuf.UnknownFieldSet.Builder unknownFields =
        com.google.protobuf.UnknownFieldSet.newBuilder();
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          case 10: {
            java.lang.String s = input.readStringRequireUtf8();

            inputName_ = s;
            break;
          }
          case 16: {

            width_ = input.readInt32();
            break;
          }
          case 24: {

            height_ = input.readInt32();
            break;
          }
          case 32: {

            channels_ = input.readInt32();
            break;
          }
          default: {
            if (!parseUnknownField(
                input, unknownFields, extensionRegistry, tag)) {
              done = true;
            }
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return org.tribuo.interop.tensorflow.protos.TribuoTensorflow.internal_static_tribuo_interop_tensorflow_ImageConverterProto_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return org.tribuo.interop.tensorflow.protos.TribuoTensorflow.internal_static_tribuo_interop_tensorflow_ImageConverterProto_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            org.tribuo.interop.tensorflow.protos.ImageConverterProto.class, org.tribuo.interop.tensorflow.protos.ImageConverterProto.Builder.class);
  }

  public static final int INPUT_NAME_FIELD_NUMBER = 1;
  private volatile java.lang.Object inputName_;
  /**
   * <code>string input_name = 1;</code>
   * @return The inputName.
   */
  @java.lang.Override
  public java.lang.String getInputName() {
    java.lang.Object ref = inputName_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      inputName_ = s;
      return s;
    }
  }
  /**
   * <code>string input_name = 1;</code>
   * @return The bytes for inputName.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getInputNameBytes() {
    java.lang.Object ref = inputName_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      inputName_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int WIDTH_FIELD_NUMBER = 2;
  private int width_;
  /**
   * <code>int32 width = 2;</code>
   * @return The width.
   */
  @java.lang.Override
  public int getWidth() {
    return width_;
  }

  public static final int HEIGHT_FIELD_NUMBER = 3;
  private int height_;
  /**
   * <code>int32 height = 3;</code>
   * @return The height.
   */
  @java.lang.Override
  public int getHeight() {
    return height_;
  }

  public static final int CHANNELS_FIELD_NUMBER = 4;
  private int channels_;
  /**
   * <code>int32 channels = 4;</code>
   * @return The channels.
   */
  @java.lang.Override
  public int getChannels() {
    return channels_;
  }

  private byte memoizedIsInitialized = -1;
  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(inputName_)) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 1, inputName_);
    }
    if (width_ != 0) {
      output.writeInt32(2, width_);
    }
    if (height_ != 0) {
      output.writeInt32(3, height_);
    }
    if (channels_ != 0) {
      output.writeInt32(4, channels_);
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(inputName_)) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, inputName_);
    }
    if (width_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(2, width_);
    }
    if (height_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(3, height_);
    }
    if (channels_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(4, channels_);
    }
    size += unknownFields.getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof org.tribuo.interop.tensorflow.protos.ImageConverterProto)) {
      return super.equals(obj);
    }
    org.tribuo.interop.tensorflow.protos.ImageConverterProto other = (org.tribuo.interop.tensorflow.protos.ImageConverterProto) obj;

    if (!getInputName()
        .equals(other.getInputName())) return false;
    if (getWidth()
        != other.getWidth()) return false;
    if (getHeight()
        != other.getHeight()) return false;
    if (getChannels()
        != other.getChannels()) return false;
    if (!unknownFields.equals(other.unknownFields)) return false;
    return true;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + INPUT_NAME_FIELD_NUMBER;
    hash = (53 * hash) + getInputName().hashCode();
    hash = (37 * hash) + WIDTH_FIELD_NUMBER;
    hash = (53 * hash) + getWidth();
    hash = (37 * hash) + HEIGHT_FIELD_NUMBER;
    hash = (53 * hash) + getHeight();
    hash = (37 * hash) + CHANNELS_FIELD_NUMBER;
    hash = (53 * hash) + getChannels();
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static org.tribuo.interop.tensorflow.protos.ImageConverterProto parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.tribuo.interop.tensorflow.protos.ImageConverterProto parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.tribuo.interop.tensorflow.protos.ImageConverterProto parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.tribuo.interop.tensorflow.protos.ImageConverterProto parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.tribuo.interop.tensorflow.protos.ImageConverterProto parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.tribuo.interop.tensorflow.protos.ImageConverterProto parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.tribuo.interop.tensorflow.protos.ImageConverterProto parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static org.tribuo.interop.tensorflow.protos.ImageConverterProto parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static org.tribuo.interop.tensorflow.protos.ImageConverterProto parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static org.tribuo.interop.tensorflow.protos.ImageConverterProto parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static org.tribuo.interop.tensorflow.protos.ImageConverterProto parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static org.tribuo.interop.tensorflow.protos.ImageConverterProto parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(org.tribuo.interop.tensorflow.protos.ImageConverterProto prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * <pre>
   *ImageConverter proto
   * </pre>
   *
   * Protobuf type {@code tribuo.interop.tensorflow.ImageConverterProto}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:tribuo.interop.tensorflow.ImageConverterProto)
      org.tribuo.interop.tensorflow.protos.ImageConverterProtoOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return org.tribuo.interop.tensorflow.protos.TribuoTensorflow.internal_static_tribuo_interop_tensorflow_ImageConverterProto_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return org.tribuo.interop.tensorflow.protos.TribuoTensorflow.internal_static_tribuo_interop_tensorflow_ImageConverterProto_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              org.tribuo.interop.tensorflow.protos.ImageConverterProto.class, org.tribuo.interop.tensorflow.protos.ImageConverterProto.Builder.class);
    }

    // Construct using org.tribuo.interop.tensorflow.protos.ImageConverterProto.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      inputName_ = "";

      width_ = 0;

      height_ = 0;

      channels_ = 0;

      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return org.tribuo.interop.tensorflow.protos.TribuoTensorflow.internal_static_tribuo_interop_tensorflow_ImageConverterProto_descriptor;
    }

    @java.lang.Override
    public org.tribuo.interop.tensorflow.protos.ImageConverterProto getDefaultInstanceForType() {
      return org.tribuo.interop.tensorflow.protos.ImageConverterProto.getDefaultInstance();
    }

    @java.lang.Override
    public org.tribuo.interop.tensorflow.protos.ImageConverterProto build() {
      org.tribuo.interop.tensorflow.protos.ImageConverterProto result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public org.tribuo.interop.tensorflow.protos.ImageConverterProto buildPartial() {
      org.tribuo.interop.tensorflow.protos.ImageConverterProto result = new org.tribuo.interop.tensorflow.protos.ImageConverterProto(this);
      result.inputName_ = inputName_;
      result.width_ = width_;
      result.height_ = height_;
      result.channels_ = channels_;
      onBuilt();
      return result;
    }

    @java.lang.Override
    public Builder clone() {
      return super.clone();
    }
    @java.lang.Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.setField(field, value);
    }
    @java.lang.Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }
    @java.lang.Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }
    @java.lang.Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return super.setRepeatedField(field, index, value);
    }
    @java.lang.Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.addRepeatedField(field, value);
    }
    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof org.tribuo.interop.tensorflow.protos.ImageConverterProto) {
        return mergeFrom((org.tribuo.interop.tensorflow.protos.ImageConverterProto)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(org.tribuo.interop.tensorflow.protos.ImageConverterProto other) {
      if (other == org.tribuo.interop.tensorflow.protos.ImageConverterProto.getDefaultInstance()) return this;
      if (!other.getInputName().isEmpty()) {
        inputName_ = other.inputName_;
        onChanged();
      }
      if (other.getWidth() != 0) {
        setWidth(other.getWidth());
      }
      if (other.getHeight() != 0) {
        setHeight(other.getHeight());
      }
      if (other.getChannels() != 0) {
        setChannels(other.getChannels());
      }
      this.mergeUnknownFields(other.unknownFields);
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      org.tribuo.interop.tensorflow.protos.ImageConverterProto parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (org.tribuo.interop.tensorflow.protos.ImageConverterProto) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private java.lang.Object inputName_ = "";
    /**
     * <code>string input_name = 1;</code>
     * @return The inputName.
     */
    public java.lang.String getInputName() {
      java.lang.Object ref = inputName_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        inputName_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string input_name = 1;</code>
     * @return The bytes for inputName.
     */
    public com.google.protobuf.ByteString
        getInputNameBytes() {
      java.lang.Object ref = inputName_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        inputName_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string input_name = 1;</code>
     * @param value The inputName to set.
     * @return This builder for chaining.
     */
    public Builder setInputName(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      inputName_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string input_name = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearInputName() {
      
      inputName_ = getDefaultInstance().getInputName();
      onChanged();
      return this;
    }
    /**
     * <code>string input_name = 1;</code>
     * @param value The bytes for inputName to set.
     * @return This builder for chaining.
     */
    public Builder setInputNameBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      inputName_ = value;
      onChanged();
      return this;
    }

    private int width_ ;
    /**
     * <code>int32 width = 2;</code>
     * @return The width.
     */
    @java.lang.Override
    public int getWidth() {
      return width_;
    }
    /**
     * <code>int32 width = 2;</code>
     * @param value The width to set.
     * @return This builder for chaining.
     */
    public Builder setWidth(int value) {
      
      width_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int32 width = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearWidth() {
      
      width_ = 0;
      onChanged();
      return this;
    }

    private int height_ ;
    /**
     * <code>int32 height = 3;</code>
     * @return The height.
     */
    @java.lang.Override
    public int getHeight() {
      return height_;
    }
    /**
     * <code>int32 height = 3;</code>
     * @param value The height to set.
     * @return This builder for chaining.
     */
    public Builder setHeight(int value) {
      
      height_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int32 height = 3;</code>
     * @return This builder for chaining.
     */
    public Builder clearHeight() {
      
      height_ = 0;
      onChanged();
      return this;
    }

    private int channels_ ;
    /**
     * <code>int32 channels = 4;</code>
     * @return The channels.
     */
    @java.lang.Override
    public int getChannels() {
      return channels_;
    }
    /**
     * <code>int32 channels = 4;</code>
     * @param value The channels to set.
     * @return This builder for chaining.
     */
    public Builder setChannels(int value) {
      
      channels_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int32 channels = 4;</code>
     * @return This builder for chaining.
     */
    public Builder clearChannels() {
      
      channels_ = 0;
      onChanged();
      return this;
    }
    @java.lang.Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:tribuo.interop.tensorflow.ImageConverterProto)
  }

  // @@protoc_insertion_point(class_scope:tribuo.interop.tensorflow.ImageConverterProto)
  private static final org.tribuo.interop.tensorflow.protos.ImageConverterProto DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new org.tribuo.interop.tensorflow.protos.ImageConverterProto();
  }

  public static org.tribuo.interop.tensorflow.protos.ImageConverterProto getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<ImageConverterProto>
      PARSER = new com.google.protobuf.AbstractParser<ImageConverterProto>() {
    @java.lang.Override
    public ImageConverterProto parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new ImageConverterProto(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<ImageConverterProto> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<ImageConverterProto> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public org.tribuo.interop.tensorflow.protos.ImageConverterProto getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}


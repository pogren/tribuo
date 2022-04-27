/*
 * Copyright (c) 2022, Oracle and/or its affiliates. All rights reserved.
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

package org.tribuo.util;

import com.google.protobuf.Any;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.GeneratedMessageV3.Builder;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.oracle.labs.mlrg.olcut.config.Configurable;
import com.oracle.labs.mlrg.olcut.config.PropertyException;
import com.oracle.labs.mlrg.olcut.util.MutableLong;
import com.oracle.labs.mlrg.olcut.util.Pair;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.tribuo.ProtoSerializable;
import org.tribuo.ProtobufClass;
import org.tribuo.ProtobufField;
import org.tribuo.hash.HashCodeHasher;
import org.tribuo.protos.core.CategoricalInfoProto;
import org.tribuo.protos.core.HasherProto;
import org.tribuo.protos.core.ModHashCodeHasherProto;
import org.tribuo.protos.core.VariableInfoProto;

/**
 * Utilities for working with Tribuo protobufs.
 */
public final class ProtoUtil {

    public static final String DESERIALIZATION_METHOD_NAME = "deserializeFromProto";

    private static final Map<Pair<Integer, String>, String> REDIRECT_MAP = new HashMap<>();

    public static <S extends Message, SD extends Message, PS extends ProtoSerializable<S, SD>> Class<S> getSType(PS ps) {
        List<Class> lineage = getLineage(ProtoSerializable.class, ps.getClass());
        System.out.println("lineage:");
        for (Class class1 : lineage) {
            System.out.println(class1.getName());
        }
        System.out.println();
        printInfo(ps.getClass(), 0);

        System.out.println("type parameter types");
        List<Class<?>> typeParamTypes = getTypeParameterTypes(ProtoSerializable.class, ps.getClass());
        for (Class<?> class1 : typeParamTypes) {
            System.out.println(class1.getName());
        }
//        return (Class<S>) getType(ProtoSerializable.class, ps.getClass(), 0);
        return null;
    }

    public static <I, C extends I> List<Class> getLineage(Class<I> intface, Class<C> clazz){
        if(!intface.isAssignableFrom(clazz)) {
            throw new RuntimeException(""+intface.getName()+" is not assignable from "+clazz.getName());
        }
        List<Class> lineage = new ArrayList<>();
        lineage.add(clazz);
        return getLineage(intface, clazz, lineage);
    }
    
    private static <I, C extends I> List<Class> getLineage(Class<I> intface, Class<C> clazz, List<Class> lineage) {
        if(clazz.equals(intface)) {
            return lineage;
        }
        Class superClass = clazz.getSuperclass();
        if(superClass != null && intface.isAssignableFrom(superClass)) {
            lineage.add(superClass);
            return getLineage(intface, superClass, lineage);
        }
        for(Class intClass : clazz.getInterfaces()) {
            if(intface.isAssignableFrom(intClass)) {
                lineage.add(intClass);
                return getLineage(intface, intClass, lineage);
            }
        }
        throw new RuntimeException("unable to create lineage from class="+clazz.getName()+" to ancestor="+intface.getName());
    }

    public static <I, C extends I> List<Class<?>> getTypeParameterTypes(Class<I> intface, Class<C> clazz) {
        TypeVariable[] typeVariables = intface.getTypeParameters();
        List<Object> typeParameterTypes = new ArrayList<>();
        for(TypeVariable typeVariable : typeVariables) {
            typeParameterTypes.add(typeVariable.getName());
        }
        
        List<Class> lineage = getLineage(intface, clazz);
        
        for(int i=lineage.size()-2; i>=0; i--) {
            Class cls = lineage.get(i);

            List<String> typeVariableNames = new ArrayList<>();
            for(TypeVariable tv : cls.getTypeParameters()) {
                typeVariableNames.add(tv.getName());
            }

            for(Type genericInterface : cls.getGenericInterfaces()) {
                if(genericInterface instanceof ParameterizedType) {
                    ParameterizedType pt = (ParameterizedType) genericInterface;
                    if(lineage.get(i+1).equals(pt.getRawType())) {
                        int j=0;
                        for(Type at : pt.getActualTypeArguments()) {
                            Object tpt = typeParameterTypes.get(j);
                            while(tpt instanceof Class) {
                                tpt = typeParameterTypes.get(++j);
                            }
                            if(at instanceof Class) {
                                typeParameterTypes.set(j, at);
                            }
                            if(at instanceof TypeVariable) {
                                typeParameterTypes.set(j, ((TypeVariable)at).getName());
                            }
                        }
                    }
                }
            }

            Type t = cls.getGenericSuperclass();
            if(t instanceof ParameterizedType) {
                ParameterizedType pt = (ParameterizedType) t;
                if(lineage.get(i+1).equals(pt.getRawType())) {
                    int j=0;
                    for(Type at : pt.getActualTypeArguments()) {
                        Object tpt = typeParameterTypes.get(j);
                        while(tpt instanceof Class) {
                            tpt = typeParameterTypes.get(++j);
                        }
                        if(at instanceof Class) {
                            typeParameterTypes.set(j, at);
                        }
                        if(at instanceof TypeVariable) {
                            typeParameterTypes.set(j, ((TypeVariable)at).getName());
                        }
                    }
                }
            }
            
        }
        
        List<Class<?>> returnValues = new ArrayList<>();
        for(Object obj : typeParameterTypes) {
            if(obj instanceof Class) {
                returnValues.add((Class<?>)obj);
            } else {
                throw new RuntimeException("type parameter unresolved: "+obj);
            }
        }
        
        return returnValues;
    }
    
    public static String pad(int padding) {
        if (padding > 0) {
            StringBuilder sb = new StringBuilder(padding);
            for (int i = 0; i < padding; i++) {
                sb.append('\t');
            }
            return sb.toString();
        } else {
            return "";
        }
    }

    private static void printInfo(Type t, int tab) {
        if(t == null || t.equals(Object.class)) {
            return;
        }
        String pad = pad(tab);
        System.out.println(pad+"type: "+t.getTypeName());
        if(t instanceof Class) {
            System.out.println(pad+"is assignable from ProtSerializable: "+ProtoSerializable.class.isAssignableFrom((Class)t));
            Type gt = ((Class)t).getGenericSuperclass();
            if(gt != null) {
                System.out.println(pad+"generic superclass: "+gt.getTypeName()+" cls="+gt.getClass());
                printInfo(gt, tab+1);
            }

            Class c = (Class)t;
            for(TypeVariable tv : c.getTypeParameters()) {
                System.out.println(pad+"tv: "+tv);
                for(Type type : tv.getBounds()) {
                    System.out.println(pad+" bound: "+type);
                }
                System.out.println(pad+" generic declaration: "+tv.getGenericDeclaration());
            }
            
            
            Class sc = ((Class)t).getSuperclass();
            if(sc != null) {
                System.out.println(pad+"superclass: "+sc);
                printInfo(sc, tab+1);
            }
            for(Type g : ((Class)t).getGenericInterfaces()) {
                System.out.println(pad+"generic interface: "+g.getTypeName()+"  cls="+g.getClass().getName());
                printInfo(g, tab+1);
            }
            for(Type i : ((Class)t).getInterfaces()) {
                System.out.println(pad+"interface: "+i.getTypeName());
                printInfo(i, tab+1);
            }
        }
        if(t instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType) t;
            System.out.println(pad+"raw type: "+pt.getRawType()); 
            System.out.println(pad+"owner type: "+pt.getOwnerType()); 
            for(Type at : pt.getActualTypeArguments()) {
                System.out.println(pad+"actual type arg: "+at+" cls="+at.getClass().getName());
//                printInfo(at, tab+1);
            }
     
        }
    }
    

    /**
     * Adds a redirect mapping to the internal redirection map.
     * <p>
     * This is used when a class name changes, to allow old protobufs to be deserialized into
     * the new class.
     * @param input The version and class name to redirect.
     * @param targetClassName The class name that should be used to deserialize the protobuf.
     */
    public static void registerRedirect(Pair<Integer, String> input, String targetClassName) {
        if (REDIRECT_MAP.containsKey(input)) {
            throw new IllegalArgumentException("Redirect map is append only, key " + input + " already has mapping " + REDIRECT_MAP.get(input));
        } else {
            REDIRECT_MAP.put(input, targetClassName);
        }
    }

    /**
     * Instantiates the class from the supplied protobuf fields.
     * <p>
     * Deserialization proceeds as follows:
     * <ul>
     *     <li>Check to see if there is a valid redirect for this version & class name tuple.
     *     If there is then the new class name is used for the following steps.</li>
     *     <li>Lookup the class name and instantiate the {@link Class} object.</li>
     *     <li>Find the 3 arg static method {@code  deserializeFromProto(int version, String className, com.google.protobuf.Any message)}.</li>
     *     <li>Call the method passing along the original three arguments (note this uses the
     *     original class name even if a redirect has been applied).</li>
     *     <li>Return the freshly constructed object, or rethrow any runtime exceptions.</li>
     * </ul>
     * <p>
     * Throws {@link IllegalStateException} if:
     * <ul>
     *     <li>the requested class could not be found on the classpath/modulepath</li>
     *     <li>the requested class does not have the necessary 3 arg constructor</li>
     *     <li>the constructor could not be invoked due to its accessibility, or is in some other way invalid</li>
     *     <li>the constructor threw an exception</li>
     * </ul>
     * @param version The version number of the protobuf.
     * @param className The class name of the serialized object.
     * @param message The object's serialized representation.
     * @return The deserialized object.
     */
    public static Object instantiate(int version, String className, Any message) {
        Pair<Integer, String> key = new Pair<>(version, className);
        String targetClassName = REDIRECT_MAP.getOrDefault(key, className);
        try {
            Class<?> targetClass = Class.forName(targetClassName);
            Method method = targetClass.getDeclaredMethod(DESERIALIZATION_METHOD_NAME, int.class, String.class, Any.class);
            method.setAccessible(true);
            Object o = method.invoke(null, version, className, message);
            method.setAccessible(false);
            return o;
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Failed to find class " + targetClassName, e);
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException("Failed to find deserialization method " + DESERIALIZATION_METHOD_NAME + "(int, String, com.google.protobuf.Any) on class " + targetClassName, e);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Failed to invoke deserialization method " + DESERIALIZATION_METHOD_NAME + "(int, String, com.google.protobuf.Any) on class " + targetClassName, e);
        } catch (InvocationTargetException e) {
            throw new IllegalStateException("The deserialization method for " + DESERIALIZATION_METHOD_NAME + "(int, String, com.google.protobuf.Any) on class " + targetClassName + " threw an exception", e);
        }
    }

    /**
     * Private final constructor for static utility class.
     */
    private ProtoUtil() {}

    
//    public static <SERIALIZED extends Message, 
//                   SERIALIZED_BUILDER extends Message.Builder,
//                   SERIALIZED_DATA extends Message.Builder, 
//                   PROTO_SERIALIZABLE extends ProtoSerializable<SERIALIZED_BUILDER>> 
//        PROTO_SERIALIZABLE deserialize(SERIALIZED serialized) {
//        
//        try {
//            //extract version from serialized
//            FieldDescriptor fieldDescriptor = serialized.getDescriptorForType().findFieldByName("version");
//            int version = ((Integer) serialized.getField(fieldDescriptor)).intValue();
//            //extract class_name of return value from serialized
//            fieldDescriptor = serialized.getDescriptorForType().findFieldByName("class_name");
//            String className = (String) serialized.getField(fieldDescriptor);
//            Class<PROTO_SERIALIZABLE> protoSerializableClass = (Class<PROTO_SERIALIZABLE>) Class.forName(className);
//
//            fieldDescriptor = serialized.getDescriptorForType().findFieldByName("serialized_data");
//            Any serializedData = (Any) serialized.getField(fieldDescriptor);
//
//            try {
//                Method method = protoSerializableClass.getDeclaredMethod(DESERIALIZATION_METHOD_NAME, int.class, String.class, Any.class);
//                method.setAccessible(true);
//                PROTO_SERIALIZABLE protoSerializable  = (PROTO_SERIALIZABLE) method.invoke(null, version, className, serializedData);
//                method.setAccessible(false);
//                return protoSerializable;
//            } catch(NoSuchMethodException nsme) {
//                return ProtoUtil.deserialize(version, className, serializedData);
//            }
//        } catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException e) {        
//            throw new RuntimeException(e);
//        }
//    }
    
//    public static <SERIALIZED extends Message,
//                   SERIALIZED_DATA extends Message, 
//                   PROTO_SERIALIZABLE extends ProtoSerializable<SERIALIZED, SERIALIZED_DATA>> 
//           PROTO_SERIALIZABLE deserialize(int version, String className, Any serializedData) {
//        try {
//            System.out.println("ProtoUtil.deserialize");
//            Class<PROTO_SERIALIZABLE> protoSerializableClass = (Class<PROTO_SERIALIZABLE>) Class.forName(className);
//            //get @ProtobuffClass annotation from class definition of serialized ProtoSerializable
//            ProtobufClass protobufClassAnnotation = protoSerializableClass.getAnnotation(ProtobufClass.class);
//            Class<? extends com.google.protobuf.GeneratedMessageV3> serializedClass = protobufClassAnnotation.serializedClass();
//            System.out.println("serialized_class: "+serializedClass.getName());
//            Class<? extends com.google.protobuf.GeneratedMessageV3> serializedDataClass = protobufClassAnnotation.serializedData();
//                        
//            System.out.println("version: " + version);
//            System.out.println("class_name: " + className);
//
//            //initialize return value
//            Constructor<PROTO_SERIALIZABLE> declaredConstructor = protoSerializableClass.getDeclaredConstructor();
//            declaredConstructor.setAccessible(true);
//            PROTO_SERIALIZABLE protoSerializable = declaredConstructor.newInstance();
//
//
//            //e.g. HashCodeHasher has no serializable data so exit early
//            if(serializedData.getValue().size() == 0) {
//                return protoSerializable;
//            }
//            GeneratedMessageV3 proto = serializedData.unpack(serializedDataClass);
//
//            System.out.println("serialized_data: " + proto.getClass().getName());
//            System.out.println("protoSerializable: "+protoSerializable.getClass().getName());
//
//            for (Field field : getFields(protoSerializableClass)) {
//                System.out.println("field: "+field.getName());
//                ProtobufField protobufField = field.getAnnotation(ProtobufField.class);
//                String fieldName = protobufField.name();
//                if (fieldName.equals(ProtobufField.DEFAULT_FIELD_NAME)) {
//                    fieldName = field.getName();
//                    System.out.println("field: "+field.getName());
//                }
//                field.setAccessible(true);
//
//                Method getter = findMethod(serializedDataClass, "get", fieldName, 0);
//                Object obj = getter.invoke(proto);
//                if(obj instanceof GeneratedMessageV3) {
//                    System.out.println("calling nested deserialize for "+fieldName);
//                    obj = deserialize((GeneratedMessageV3)obj);
//                }
//                System.out.println("obj = "+obj.getClass().getName());
//                field.set(protoSerializable, obj);
//            }
//            
//            if(protoSerializable instanceof Configurable) {
//                ((Configurable)protoSerializable).postConfig();
//            }
//            return protoSerializable;
//
//        } catch (ClassNotFoundException | IOException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | PropertyException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public static <S extends Message, SD extends Message, PS extends ProtoSerializable<S, SD>> 
        S serialize(PS protoSerializable) {
        try {
            List<Class<?>> typeParameterTypes = getTypeParameterTypes(ProtoSerializable.class, protoSerializable.getClass());
            
            ProtobufClass annotation = protoSerializable.getClass().getAnnotation(ProtobufClass.class);
            int version = 0;
            if (annotation != null) {
                version = annotation.version();
            }

            Class<S> serializedClass = (Class<S>) typeParameterTypes.get(0);
            S.Builder serializedClassBuilder = (S.Builder) serializedClass.getMethod("newBuilder").invoke(null);
            Class<S.Builder> serializedClassBuilderClass = (Class<S.Builder>) serializedClassBuilder.getClass();
            serializedClassBuilderClass.getMethod("setVersion", Integer.TYPE).invoke(serializedClassBuilder, version);
            serializedClassBuilderClass.getMethod("setClassName", String.class).invoke(serializedClassBuilder, protoSerializable.getClass().getName());
            SD.Builder serializedDataBuilder = protoSerializable.subserialize();
            serializedClassBuilderClass.getMethod("setSerializedData", com.google.protobuf.Any.class).invoke(serializedClassBuilder, Any.pack(serializedDataBuilder.build()));      
            return (S) serializedClassBuilder.build();
        } catch(InvocationTargetException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static <S extends Message, SD extends Message, B extends SD.Builder, PS extends ProtoSerializable<S, SD>>  
         B subserialize(PS protoSerializable) {
            try {
                List<Class<?>> typeParameterTypes = getTypeParameterTypes(ProtoSerializable.class, protoSerializable.getClass());
                Class<SD> serializedDataClass = (Class<SD>) typeParameterTypes.get(1);
                B serializedDataBuilder = (B) serializedDataClass.getMethod("newBuilder").invoke(null);
                Class<SD.Builder> serializedDataBuilderClass = (Class<SD.Builder>) serializedDataBuilder.getClass();
    
                for (Field field : getFields(protoSerializable.getClass())) {
                    ProtobufField protobufField = field.getAnnotation(ProtobufField.class);
                    String fieldName = protobufField.name();
                    if (fieldName.equals(ProtobufField.DEFAULT_FIELD_NAME)) {
                        fieldName = field.getName();
                    }
                    
                    field.setAccessible(true);
                    Object obj = field.get(protoSerializable);
                    Method setter;
                    if (obj instanceof ProtoSerializable) {
                        obj = ((ProtoSerializable) obj).serialize();
                        setter = findMethod(serializedDataBuilderClass, "set", fieldName, 1);
                    } else if (obj instanceof Iterable) {
                        obj = toList((Iterable) obj);
                        setter = findMethod(serializedDataBuilderClass, "addAll", fieldName, 1);
                    } else if (obj instanceof Map) {
                        obj = toList((Map) obj);
                        setter = findMethod(serializedDataBuilderClass, "addAll", fieldName, 1);
                    } else {
                        obj = convert(obj);
                        setter = findMethod(serializedDataBuilderClass, "set", fieldName, 1);
                    }
                    
                    setter.setAccessible(true);
                    setter.invoke(serializedDataBuilder, obj);
                }
    
                for (Field field : getMapFields(protoSerializable.getClass())) {
                    ProtobufField[] protobufFields = field.getAnnotationsByType(ProtobufField.class);
                    ProtobufField keyField = protobufFields[0];
                    ProtobufField valueField = protobufFields[1];
    
                    Method keyAdder = findMethod(serializedDataBuilderClass, "add", keyField.name(), 1);
                    keyAdder.setAccessible(true);
                    Method valueAdder = findMethod(serializedDataBuilderClass, "add", valueField.name(), 1);
                    valueAdder.setAccessible(true);
                    field.setAccessible(true);
    
                    Map map = (Map) field.get(protoSerializable);
                    if(map != null) {
                        Set<Map.Entry> entrySet = map.entrySet();
                        for (Map.Entry e : entrySet) {
                            keyAdder.invoke(serializedDataBuilder, convert(e.getKey()));
                            valueAdder.invoke(serializedDataBuilder, convert(e.getValue()));
                        }
                    }
                }
                return serializedDataBuilder;
            } catch (InvocationTargetException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException
                | SecurityException e) {
                throw new RuntimeException(e);
        }
    }

    private static List toList(Iterable iterable) {
        List values = new ArrayList();
        for(Object value : iterable) {
            if(value instanceof ProtoSerializable) {
                value = ((ProtoSerializable) value).serialize();
            } else {
                value = convert(value);
            }
            values.add(value);
        }
        return values;
    }

    
    private static List toList(Map obj) {
        return toList(obj.values());
    }

    private static Object convert(Object obj) {
        if (obj instanceof MutableLong) {
            return ((MutableLong) obj).longValue();
        }
        return obj;
    }

    private static List<Field> getMapFields(Class<? extends ProtoSerializable> class1) {
        Set<String> fieldNameSet = new HashSet<>();
        List<Field> fields = new ArrayList<>();
        for (Field field : class1.getDeclaredFields()) {
            ProtobufField[] protobufFields = field.getAnnotationsByType(ProtobufField.class);
            if (protobufFields.length == 2) {
                Class<?> fieldType = field.getType();
                if (Map.class.isAssignableFrom(fieldType)) {
                    if (fieldNameSet.contains(field.getName()))
                        continue;
                    fields.add(field);
                    fieldNameSet.add(field.getName());
                }
            }
        }
        Class<?> superclass = class1.getSuperclass();
        if (ProtoSerializable.class.isAssignableFrom(superclass)) {
            List<Field> superfields = getMapFields((Class<? extends ProtoSerializable>) superclass);
            for (Field field : superfields) {
                if (fieldNameSet.contains(field.getName()))
                    continue;
                fields.add(field);
                fieldNameSet.add(field.getName());
            }
        }
        return fields;
    }

    private static List<Field> getFields(Class<? extends ProtoSerializable> class1) {
        Set<String> fieldNameSet = new HashSet<>();
        List<Field> fields = new ArrayList<>();
        for (Field field : class1.getDeclaredFields()) {
            ProtobufField protobufField = field.getAnnotation(ProtobufField.class);
            if (protobufField == null)
                continue;
            if (fieldNameSet.contains(field.getName()))
                continue;
            fields.add(field);
            fieldNameSet.add(field.getName());
        }

        Class<?> superclass = class1.getSuperclass();
        if (ProtoSerializable.class.isAssignableFrom(superclass)) {
            List<Field> superfields = getFields((Class<? extends ProtoSerializable>) superclass);
            for (Field field : superfields) {
                if (fieldNameSet.contains(field.getName()))
                    continue;
                fields.add(field);
                fieldNameSet.add(field.getName());
            }

        }

        return fields;
    }

    private static Method findMethod(Class<?> serializedDataBuilderClass, String prefixName, String fieldName, int expectedParamCount) {
        String methodName = generateMethodName(prefixName, fieldName);

        for (Method method : serializedDataBuilderClass.getMethods()) {
            if (method.getName().equals(methodName)) {
                if(method.getParameterTypes().length != expectedParamCount) {
                    continue;
                }
                if(expectedParamCount == 0) {
                    return method;
                }
                Class<?> class1 = method.getParameterTypes()[0];
                if(com.google.protobuf.GeneratedMessageV3.Builder.class.isAssignableFrom(class1)) {
                    continue;
                }
                return method;
            }
        }
        throw new IllegalArgumentException("unable to find method "+methodName+" for field name: " + fieldName + " in class: "
                + serializedDataBuilderClass.getName());
    }


    public static String generateMethodName(String prefix, String name) {
        StringBuilder sb = new StringBuilder();
        sb.append(prefix);
        sb.append(("" + name.charAt(0)).toUpperCase());
        sb.append(name.substring(1));
        return sb.toString();
    }
}

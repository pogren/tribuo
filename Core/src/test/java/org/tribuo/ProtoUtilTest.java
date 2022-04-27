package org.tribuo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.tribuo.hash.HashCodeHasher;
import org.tribuo.hash.HashedFeatureMap;
import org.tribuo.hash.Hasher;
import org.tribuo.hash.MessageDigestHasher;
import org.tribuo.hash.ModHashCodeHasher;
import org.tribuo.protos.core.CategoricalInfoProto;
import org.tribuo.protos.core.FeatureDomainProto;
import org.tribuo.protos.core.HashedFeatureMapProto;
import org.tribuo.protos.core.HasherProto;
import org.tribuo.protos.core.MessageDigestHasherProto;
import org.tribuo.protos.core.ModHashCodeHasherProto;
import org.tribuo.protos.core.RealInfoProto;
import org.tribuo.protos.core.VariableInfoProto;
import org.tribuo.util.ProtoUtil;

public class ProtoUtilTest {

    @Test
    void testTypes() throws Exception {
        CategoricalInfo info = new CategoricalInfo("cat");
        IntStream.range(0, 10).forEach(i -> {
            IntStream.range(0, i*2).forEach(j -> {
                info.observe(i);
            });
        });
        
        ProtoUtil.getSType(info);

        CategoricalIDInfo idInfo = info.makeIDInfo(12345);

        ProtoUtil.getSType(idInfo);
        
    }

    @Test
    void testDeserialize() throws Exception {
        MutableFeatureMap mfm = new MutableFeatureMap();
        mfm.add("goldrat", 1.618033988749);
        mfm.add("e", Math.E);
        mfm.add("pi", Math.PI);
        HashedFeatureMap hfm = HashedFeatureMap.generateHashedFeatureMap(mfm, new MessageDigestHasher("SHA-512", "abcdefghi"));
        System.out.println(hfm.toString());
        FeatureDomainProto fdp = hfm.serialize();
//        hfm = ProtoUtil.deserialize(fdp);
//        System.out.println(hfm.toString());
    }
    
    @Test
    void testHashedFeatureMap() throws Exception {
        MutableFeatureMap mfm = new MutableFeatureMap(); 
        mfm.add("goldrat", 1.618033988749);
        mfm.add("e", Math.E);
        mfm.add("pi", Math.PI);
        HashedFeatureMap hfm = HashedFeatureMap.generateHashedFeatureMap(mfm, new MessageDigestHasher("SHA-512", "abcdefghi"));
//        FeatureDomainProto fdp = ProtoUtil.serialize(hfm);
        FeatureDomainProto fdp = hfm.serialize();
        
        assertEquals(0, fdp.getVersion());
        assertEquals("org.tribuo.hash.HashedFeatureMap", fdp.getClassName());
        HashedFeatureMapProto hfmp = fdp.getSerializedData().unpack(HashedFeatureMapProto.class);
        HasherProto hasherProto = hfmp.getHasher();
        assertEquals(0, hasherProto.getVersion());
        assertEquals("org.tribuo.hash.MessageDigestHasher", hasherProto.getClassName());
        MessageDigestHasherProto mdhp = hasherProto.getSerializedData().unpack(MessageDigestHasherProto.class);
        assertEquals("SHA-512", mdhp.getHashType());
        
//        HashedFeatureMap hfmDeserialized = ProtoUtil.deserialize(fdp);
//        hfmDeserialized.setSalt("abcdefghi");

//        assertEquals(hfm, hfmDeserialized);
        
        
    }
    

    @Test
    void testModHashCodeHasher() throws Exception {
        ModHashCodeHasher hasher = new ModHashCodeHasher(200, "abcdefghi");
        HasherProto hasherProto = ProtoUtil.serialize(hasher);
        assertEquals(0, hasherProto.getVersion());
        assertEquals("org.tribuo.hash.ModHashCodeHasher", hasherProto.getClassName());
        ModHashCodeHasherProto proto = hasherProto.getSerializedData().unpack(ModHashCodeHasherProto.class);
        assertEquals(200, proto.getDimension());

//        ModHashCodeHasher dHasher = ProtoUtil.deserialize(hasherProto);
//        dHasher.setSalt("abcdefghi");
//        assertEquals(hasher, dHasher);
    }
    
    @Test
    void testMessageDigestHasher() throws Exception {
        MessageDigestHasher hasher = new MessageDigestHasher("SHA-256", "abcdefghi");
        HasherProto hasherProto = ProtoUtil.serialize(hasher);
        assertEquals(0, hasherProto.getVersion());
        assertEquals("org.tribuo.hash.MessageDigestHasher", hasherProto.getClassName());
        MessageDigestHasherProto proto = hasherProto.getSerializedData().unpack(MessageDigestHasherProto.class);
        assertEquals("SHA-256", proto.getHashType());
        
//        MessageDigestHasher dHasher = ProtoUtil.deserialize(hasherProto);
//        dHasher.setSalt("abcdefghi");
//        assertEquals(hasher, dHasher);
    }

    @Test
    void testHashCodeHasher() throws Exception {
        HashCodeHasher hasher = new HashCodeHasher("abcdefghi");
        HasherProto hasherProto = ProtoUtil.serialize(hasher);
        assertEquals(0, hasherProto.getVersion());
        assertEquals("org.tribuo.hash.HashCodeHasher", hasherProto.getClassName());
        
//        HashCodeHasher dHasher = ProtoUtil.deserialize(hasherProto);
//        dHasher.setSalt("abcdefghi");
//        assertEquals(hasher, dHasher);
    }

    
    @Test
    void testRealIDInfo() throws Exception {
        VariableInfo info = new RealIDInfo("bob", 100, 1000.0, 0.0, 25.0, 125.0, 12345);
        VariableInfoProto infoProto = ProtoUtil.serialize(info);
        assertEquals(0, infoProto.getVersion());
        assertEquals("org.tribuo.RealIDInfo", infoProto.getClassName());
        RealInfoProto proto = infoProto.getSerializedData().unpack(RealInfoProto.class);
        assertEquals("bob", proto.getName());
        assertEquals(100, proto.getCount());
        assertEquals(1000.0, proto.getMax());
        assertEquals(0.0, proto.getMin());
        assertEquals(25.0, proto.getMean());
        assertEquals(125.0, proto.getSumSquares());
        assertEquals(12345, proto.getId());
        
//        VariableInfo dInfo = ProtoUtil.deserialize(infoProto);
//        assertEquals(info, dInfo);
    }
    
    @Test
    void testRealInfo() throws Exception {
        VariableInfo info = new RealInfo("bob", 100, 1000.0, 0.0, 25.0, 125.0);
        VariableInfoProto infoProto = ProtoUtil.serialize(info);
        assertEquals(0, infoProto.getVersion());
        assertEquals("org.tribuo.RealInfo", infoProto.getClassName());
        RealInfoProto proto = infoProto.getSerializedData().unpack(RealInfoProto.class);
        assertEquals("bob", proto.getName());
        assertEquals(100, proto.getCount());
        assertEquals(1000.0, proto.getMax());
        assertEquals(0.0, proto.getMin());
        assertEquals(25.0, proto.getMean());
        assertEquals(125.0, proto.getSumSquares());
        assertEquals(-1, proto.getId());
        
//        VariableInfo dInfo = ProtoUtil.deserialize(infoProto);
//        assertEquals(info, dInfo);

    }

    
    @Test
    void testCategoricalInfo() throws Exception {
        CategoricalInfo info = new CategoricalInfo("cat");
        IntStream.range(0, 10).forEach(i -> {
            IntStream.range(0, i*2).forEach(j -> {
                info.observe(i);
            });
        });
        
        VariableInfoProto infoProto = ProtoUtil.serialize(info);
        assertEquals(0, infoProto.getVersion());
        assertEquals("org.tribuo.CategoricalInfo", infoProto.getClassName());
        CategoricalInfoProto proto = infoProto.getSerializedData().unpack(CategoricalInfoProto.class);
        assertEquals("cat", proto.getName());
        assertEquals(90, proto.getCount());
        assertEquals(-1, proto.getId());
        assertEquals(0, proto.getObservedCount());
        assertEquals(Double.NaN, proto.getObservedValue());
        
        List<Double> keyList = proto.getKeyList();
        List<Long> valueList = proto.getValueList();

        assertEquals(keyList.size(), valueList.size());
        
        Map<Double, Long> expectedCounts = new HashMap<>();
        IntStream.range(0, 10).forEach(i -> {
            long count = info.getObservationCount(i);
            expectedCounts.put((double)i, count);
        });
        
        for (int i=0; i<keyList.size(); i++) {
            assertEquals(expectedCounts.get(keyList.get(i)), valueList.get(i));
        }
        
//        CategoricalInfo dInfo = ProtoUtil.deserialize(infoProto);
//        assertEquals(info, dInfo);
        
    }

    @Test
    void testCategoricalInfo2() throws Exception {
        CategoricalInfo info = new CategoricalInfo("cat");
        IntStream.range(0, 10).forEach(i -> {
            info.observe(5);
        });
        
        VariableInfoProto infoProto = ProtoUtil.serialize(info);
        assertEquals(0, infoProto.getVersion());
        assertEquals("org.tribuo.CategoricalInfo", infoProto.getClassName());
        CategoricalInfoProto proto = infoProto.getSerializedData().unpack(CategoricalInfoProto.class);
        assertEquals("cat", proto.getName());
        assertEquals(10, proto.getCount());
        assertEquals(-1, proto.getId());
        
        List<Double> keyList = proto.getKeyList();
        List<Long> valueList = proto.getValueList();

        assertEquals(0, keyList.size());
        assertEquals(0, valueList.size());
        assertEquals(5, proto.getObservedValue());
        assertEquals(10, proto.getObservedCount());
        
//        CategoricalInfo dInfo = ProtoUtil.deserialize(infoProto);
//        assertEquals(info, dInfo);

    }

    @Test
    void testCategoricalIdInfo() throws Exception {
        CategoricalInfo info = new CategoricalInfo("cat");
        IntStream.range(0, 10).forEach(i -> {
            IntStream.range(0, i*2).forEach(j -> {
                info.observe(i);
            });
        });

        CategoricalIDInfo idInfo = info.makeIDInfo(12345);
        
        VariableInfoProto infoProto = ProtoUtil.serialize(idInfo);
        assertEquals(0, infoProto.getVersion());
        assertEquals("org.tribuo.CategoricalIDInfo", infoProto.getClassName());
        CategoricalInfoProto proto = infoProto.getSerializedData().unpack(CategoricalInfoProto.class);
        assertEquals("cat", proto.getName());
        assertEquals(90, proto.getCount());
        assertEquals(12345, proto.getId());
        assertEquals(0, proto.getObservedCount());
        assertEquals(Double.NaN, proto.getObservedValue());
        
        List<Double> keyList = proto.getKeyList();
        List<Long> valueList = proto.getValueList();

        assertEquals(keyList.size(), valueList.size());
        
        Map<Double, Long> expectedCounts = new HashMap<>();
        IntStream.range(0, 10).forEach(i -> {
            long count = idInfo.getObservationCount(i);
            expectedCounts.put((double)i, count);
        });
        
        for (int i=0; i<keyList.size(); i++) {
            assertEquals(expectedCounts.get(keyList.get(i)), valueList.get(i));
        }
        
//        CategoricalInfo dInfo = ProtoUtil.deserialize(infoProto);
//        assertEquals(idInfo, dInfo);

    }

    
    
}

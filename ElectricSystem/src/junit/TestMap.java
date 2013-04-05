package junit;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Test;

public class TestMap {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void TestMap(){
		Map map = new HashMap();
		map.put("a", "aaa");
		map.put("b", "bbb");
		map.put("c", "ccc");
		
		//传统方法
		Set entrys =  map.entrySet();
		Iterator it = entrys.iterator();
		
		while(it.hasNext()){
			Entry entry = (Entry) it.next();
			String key = (String) entry.getKey();
			String value = (String) entry.getValue();
			System.err.println(key+"..."+value);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void TestMap2(){
		Map map = new HashMap();
		map.put("a", "aaa");
		map.put("b", "bbb");
		map.put("c", "ccc");
		
		//增强for循环方法
		for(Object obj : map.entrySet()){
			Entry entry = (Entry) obj;
			String key = (String) entry.getKey();
			String value = (String) entry.getValue();
			System.err.println(key+"..."+value);
		}
		
	}
	
	
	
}

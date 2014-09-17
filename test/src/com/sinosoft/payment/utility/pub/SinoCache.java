package com.sinosoft.payment.utility.pub;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class SinoCache {
	static Log log = LogFactory.getLog(SinoCache.class);
	static CacheManager cacheManager = null;
	static{
		System.setProperty("ehcache.disk.store.dir", System.getProperty("java.io.tmpdir")+"/ehcache/budget/diskstore");
		cacheManager = CacheManager.create();
	}
	public SinoCache(String cacheName){
		this.cacheName = cacheName;
	}
	private String cacheName = null;
	
	private static Cache getCache(String name){
		Cache cache = cacheManager.getCache(name);
		if(cache == null){
			cacheManager.addCache(name);
			cache = cacheManager.getCache(name);
		}
		return cache;
	}
	@SuppressWarnings("unchecked")
	public <V> V get(String busiName){
		if(log.isDebugEnabled()) log.debug(String.format("get cache : cachename=%s businame=%s", cacheName,busiName));
		Element element = getCache(cacheName).get(busiName);
		if(log.isDebugEnabled() && element==null) log.debug("businame='"+busiName+"' not got from cache.");
		if(element==null) return null;
		
		return (V)element.getObjectValue();
	}
	public void put(String busiName,Object busiValue){
		if(log.isDebugEnabled()) 
			log.debug(String.format("put into cache : cachename=%s businame=%s busiValue=%s", cacheName,busiName,busiValue));
		getCache(cacheName).put(new Element(busiName,busiValue));
	}
	public static String getCurrentStackPosition(){
		StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
		return ste.getClassName()+"."+ste.getMethodName()+ste.getLineNumber();
	}
	public static void main(String args[]){
		System.out.println(SinoCache.getCurrentStackPosition());
	}
}

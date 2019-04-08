package com.ssm.tsy.dao;

import java.util.List;
import java.util.Map;

import com.ssm.tsy.bean.TsyScillorPic;

public interface TsyScillorPicDao {
    public int deleteByPrimaryKey(Integer id);

    public int insert(TsyScillorPic record);

    public int insertSelective(TsyScillorPic record);

    public TsyScillorPic selectByPrimaryKey(Integer id);

    public int updateByPrimaryKeySelective(TsyScillorPic record);

    public int updateByPrimaryKeyWithBLOBs(TsyScillorPic record);

    public int updateByPrimaryKey(TsyScillorPic record);
    
    public List<TsyScillorPic> SelectAll();

    public TsyScillorPic selectByPrimaryKey(int id);
    
    public List<Map<String,Object>> queryTsyScillorlist(Map<String,Object> map) throws Exception;
    
    public List<Map<String,Object>> queryTsyScillorTablelist(Map<String,Object> map) throws Exception;
    
    public int updateTsyScillorPicFb(Map<String,Object> map) throws Exception;
    
    public List<Map<String,Object>> queryTsyScillorItemsByIdToTen(Map<String,Object> map) throws Exception;
    
	public Map<String,Object> queryTsyScillorItemsContentById(Map<String,Object> map) throws Exception;
	
	public int updateTsyScillorYdl(Map<String,Object> map) throws Exception;

	public List<Map<String, Object>> queryTsyScillorItemsAll(Map<String, Object> map) throws Exception;
}
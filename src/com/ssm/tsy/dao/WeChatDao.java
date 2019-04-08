package com.ssm.tsy.dao;

import java.util.List;
import java.util.Map;

import com.ssm.tsy.bean.WeChatUser;

public interface WeChatDao {
	
	/**
	 * 查询所有关注人
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> SelectAllToMap(Map<String,Object> map) throws Exception;

	/**
	 * 添加新的关注人
	 * 
	 * @param bean
	 * @return
	 */
	public int AddWeChat(WeChatUser bean);

	/**
	 * 查询所有的关注人
	 * 
	 * @return
	 */
	public List<WeChatUser> SelectAll();

	/**
	 * 根据昵称查询关注人
	 * 
	 * @return
	 */
	public List<WeChatUser> SelectAllByNickName(String nickname);

	/**
	 * 修改关注人信息 ->冻结用户 ->解除冻结 ->添加绑定信息 ->解除绑定信息 ->取消关注 ->重新关注
	 * ->更新关注人信息---头像---性别---分组---昵称---城市
	 * 
	 * @param bean
	 * @return
	 */
	public int UpdateWeChat(WeChatUser bean);

	/**
	 * 根据用户的标识来查询该用户是否存在
	 * 
	 * @param openid
	 * @return
	 */
	public WeChatUser SelectByOpenId(String openid);

}

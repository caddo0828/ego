package com.ego.dubbo.service.impl;

import java.util.List;

import javax.annotation.Resource;
import com.ego.dubbo.service.TbUserDubboService;
import com.ego.mapper.TbUserMapper;
import com.ego.pojo.TbUser;
import com.ego.pojo.TbUserExample;

public class TbUserDubboServiceImpl implements TbUserDubboService{
	@Resource
	private TbUserMapper tbUserMapper;
	
	@Override
	public TbUser selUser(TbUser user) {
		TbUserExample example = new TbUserExample();
		example.createCriteria().andUsernameEqualTo(user.getUsername()).andPasswordEqualTo(user.getPassword());
		//注意此处如果直接将集合转换成对象的化，如果该集合为空时，转对象报空指针异常
		List<TbUser> list = tbUserMapper.selectByExample(example);
		if(list!=null&&list.size()>0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public TbUser selByName(String username) {
		TbUserExample example = new TbUserExample();
		example.createCriteria().andUsernameEqualTo(username);
		List<TbUser> list = tbUserMapper.selectByExample(example);
		if(list!=null&&list.size()>0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public TbUser selByPhone(String phone) {
		TbUserExample example = new TbUserExample();
		example.createCriteria().andPhoneEqualTo(phone);
		List<TbUser> list = tbUserMapper.selectByExample(example);
		if(list!=null&&list.size()>0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public int insUser(TbUser tbUser) throws Exception {
		int index = tbUserMapper.insertSelective(tbUser);
		if(index>=1) {
			return 1;
		}else {
			throw new Exception("用户注册失败，数据回滚");
		}
	}

}

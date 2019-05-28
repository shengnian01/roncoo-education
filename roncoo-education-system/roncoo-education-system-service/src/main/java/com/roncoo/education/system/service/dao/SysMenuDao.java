package com.roncoo.education.system.service.dao;

import java.util.List;

import com.roncoo.education.system.service.dao.impl.mapper.entity.SysMenu;
import com.roncoo.education.system.service.dao.impl.mapper.entity.SysMenuExample;
import com.roncoo.education.util.base.Page;

public interface SysMenuDao {
	int save(SysMenu record);

	int deleteById(Long id);

	int updateById(SysMenu record);

	int updateByExampleSelective(SysMenu record, SysMenuExample example);

	SysMenu getById(Long id);

	Page<SysMenu> listForPage(int pageCurrent, int pageSize, SysMenuExample example);

	/**
	 * 根据父ID获取菜单
	 * 
	 * @param parentId
	 * @return
	 */
	List<SysMenu> listByParentId(Long parentId);

	/**
	 * 列出所有菜单
	 * 
	 * @return
	 */
	List<SysMenu> listAll();
}
package com.roncoo.education.system.service.biz.pc;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.roncoo.education.system.service.common.req.SysMenuDeleteREQ;
import com.roncoo.education.system.service.common.req.SysMenuSaveREQ;
import com.roncoo.education.system.service.common.req.SysMenuUpdateREQ;
import com.roncoo.education.system.service.common.req.SysMenuViewREQ;
import com.roncoo.education.system.service.common.resq.SysMenuListRESQ;
import com.roncoo.education.system.service.common.resq.SysMenuRESQ;
import com.roncoo.education.system.service.common.resq.SysMenuViewRESQ;
import com.roncoo.education.system.service.dao.SysMenuDao;
import com.roncoo.education.system.service.dao.impl.mapper.entity.SysMenu;
import com.roncoo.education.util.base.PageUtil;
import com.roncoo.education.util.base.Result;
import com.roncoo.education.util.enums.ResultEnum;
import com.roncoo.education.util.tools.BeanUtil;
import com.xiaoleilu.hutool.util.CollectionUtil;
import com.xiaoleilu.hutool.util.ObjectUtil;

/**
 * 菜单信息
 *
 * @author wujing
 */
@Component
public class PcApiSysMenuBiz {

	@Autowired
	private SysMenuDao dao;

	public Result<SysMenuListRESQ> list() {
		SysMenuListRESQ resq = new SysMenuListRESQ();
		List<SysMenu> list = dao.listAll();
		if (CollectionUtils.isNotEmpty(list)) {
			resq.setSysMenu(PageUtil.copyList(list, SysMenuRESQ.class));
		}
		return Result.success(resq);
	}

	public Result<Integer> save(SysMenuSaveREQ req) {
		SysMenu record = BeanUtil.copyProperties(req, SysMenu.class);
		int results = dao.save(record);
		if (results > 0) {
			return Result.success(results);
		}
		return Result.error(ResultEnum.SYSTEM_SAVE_FAIL);
	}

	@Transactional
	public Result<Integer> delete(SysMenuDeleteREQ req) {
		if (req.getId() == null) {
			return Result.error("主键ID不能为空");
		}
		// 删除菜单,存在子菜单则迭代删除子菜单
		List<SysMenu> list = dao.listByParentId(req.getId());
		if (CollectionUtil.isNotEmpty(list)) {
			for (SysMenu sysMenu : list) {
				dao.deleteById(sysMenu.getId());
			}
		}
		int results = dao.deleteById(req.getId());
		if (results > 0) {
			return Result.success(results);
		}
		return Result.error(ResultEnum.SYSTEM_DELETE_FAIL);
	}

	public Result<Integer> update(SysMenuUpdateREQ req) {
		if (req.getId() == null) {
			return Result.error("主键ID不能为空");
		}
		SysMenu sysMenu = dao.getById(req.getId());
		if (ObjectUtil.isNull(sysMenu)) {
			return Result.error("找不到菜单信息");
		}
		SysMenu record = BeanUtil.copyProperties(req, SysMenu.class);
		int results = dao.updateById(record);
		if (results > 0) {
			return Result.success(results);
		}
		return Result.error(ResultEnum.SYSTEM_UPDATE_FAIL);
	}

	public Result<SysMenuViewRESQ> view(SysMenuViewREQ req) {
		if (req.getId() == null) {
			return Result.error("主键ID不能为空");
		}
		SysMenu record = dao.getById(req.getId());
		if (ObjectUtil.isNull(record)) {
			return Result.error("找不到菜单信息");
		}
		return Result.success(BeanUtil.copyProperties(record, SysMenuViewRESQ.class));
	}

}

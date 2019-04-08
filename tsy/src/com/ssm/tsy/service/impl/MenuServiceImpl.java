package com.ssm.tsy.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssm.tsy.bean.MenuBean;
import com.ssm.tsy.bean.wechat.Button;
import com.ssm.tsy.bean.wechat.CommonButton;
import com.ssm.tsy.bean.wechat.ComplexButton;
import com.ssm.tsy.bean.wechat.Menu;
import com.ssm.tsy.bean.wechat.ViewButton;
import com.ssm.tsy.dao.MenuDao;
import com.ssm.tsy.object.InputObject;
import com.ssm.tsy.object.OutputObject;
import com.ssm.tsy.service.MenuService;
import com.ssm.tsy.util.Constants;

@Service
public class MenuServiceImpl implements MenuService {

	@Resource
	private MenuDao menuDao;

	/**
	 * 查询所有自定义菜单
	 * 
	 * @return
	 */
	@Override
	public List<MenuBean> SelectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 查询单项自定义菜单
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public MenuBean SelectItemById(int id) {
		return menuDao.SelectItemById(id);
	}

	/**
	 * 根据一级菜单查询子菜单
	 * 
	 * @return
	 */
	@Override
	public List<MenuBean> SelectAllByBolong(int bolong) {
		return menuDao.SelectAllByBolong(bolong);
	}

	/**
	 * 查询所有的一级菜单或者二级菜单
	 * 
	 * @return
	 */
	@Override
	public List<MenuBean> SelectAllByLevel(int level) {
		return menuDao.SelectAllByLevel(level);
	}

	/**
	 * 根据菜单名称查询菜单
	 * 
	 * @return
	 */
	@Override
	public List<MenuBean> SelectAllByName(String menuname) {
		return menuDao.SelectAllByName(menuname);
	}

	/**
	 * 添加自定义菜单
	 * 
	 * @param bean
	 * @return
	 */
	@Override
	public int AddMenuBean(MenuBean bean) {
		return menuDao.AddMenuBean(bean);
	}

	/**
	 * 修改自定义菜单
	 * 
	 * @param bean
	 * @return
	 */
	@Override
	public int UpdateMenuBean(MenuBean bean) {
		return menuDao.UpdateMenuBean(bean);
	}

	/**
	 * 删除自定义菜单
	 * 
	 * @param bean
	 * @return
	 */
	@Override
	public int DeleteMenuBean(int id) {
		int size = 0;
		// 判断该菜单是否含有二级菜单，如果直接是二级菜单或者该一级菜单没有二级菜单，则直接进行else
		if (menuDao.SelectItemById(id).getHaschild() == 1) {
			List<MenuBean> bean_list = menuDao.SelectAllByBolong(id);
			for (MenuBean bean : bean_list) {
				menuDao.DeleteMenuBean(bean.getId());
			}
			size = menuDao.DeleteMenuBean(id);
		} else {
			// 判断要删除的二级菜单所属的一级菜单是否还有二级菜单
			if (menuDao.SelectItemById(id).getMenubolong() == 0) {
				size = menuDao.DeleteMenuBean(id);
			} else {
				List<MenuBean> bean_list = menuDao.SelectAllByBolong(menuDao.SelectItemById(id).getMenubolong());
				if (bean_list.size() == 1) {
					MenuBean bean = menuDao.SelectItemById(menuDao.SelectItemById(id).getMenubolong());
					bean.setHaschild(0);
					bean.setMenutype("click");
					menuDao.UpdateMenuBean(bean);
					size = menuDao.DeleteMenuBean(id);
				} else {
					size = menuDao.DeleteMenuBean(id);
				}
			}
		}
		return size;
	}

	/**
	 * 发布菜单
	 */
	@Override
	public Menu getMenu() {
		// 查询所有的一级菜单
		List<MenuBean> yi_list = menuDao.SelectAllByLevel(1);
		// 查询所有的二级菜单
		List<MenuBean> er_list = menuDao.SelectAllByLevel(2);
		Menu menu = new Menu();
		// 用来存储一级菜单
		List<ComplexButton> btn_0 = new ArrayList<ComplexButton>();
		List<ViewButton> btn_1 = new ArrayList<ViewButton>();
		List<CommonButton> btn_2 = new ArrayList<CommonButton>();
		for (MenuBean bean : yi_list) {
			ComplexButton mainBtn = new ComplexButton();
			if (bean.getHaschild() == 1) {
				// 如果一级菜单下面有二级菜单
				mainBtn.setName(bean.getMenuname());
				// 用来存储二级菜单
				List<CommonButton> btn1 = new ArrayList<CommonButton>();
				List<ViewButton> btn2 = new ArrayList<ViewButton>();
				for (MenuBean bean1 : er_list) {
					if (bean1.getMenubolong() == bean.getId()&& bean1.getMenutype().equals("click")) {
						// 恢复内容的菜单
						CommonButton btnbe = new CommonButton();
						btnbe.setName(bean1.getMenuname());
						btnbe.setType(bean1.getMenutype());
						btnbe.setKey(bean1.getMenukey());
						btn1.add(btnbe);
					} else if (bean1.getMenubolong() == bean.getId()&& bean1.getMenutype().equals("view")) {
						// 跳转网页的菜单
						ViewButton btnbe = new ViewButton();
						btnbe.setName(bean1.getMenuname());
						btnbe.setType(bean1.getMenutype());
						btnbe.setUrl(bean1.getRebackcontext());
						btn2.add(btnbe);
					}
				}
				switch (btn1.size() + btn2.size()) {
				case 1:
					switch (btn1.size()) {
					case 0:mainBtn.setSub_button(new Button[] { btn2.get(0) });break;
					case 1:mainBtn.setSub_button(new Button[] { btn1.get(0) });break;
					default:break;
					}break;
				case 2:
					switch (btn1.size()) {
					case 0:mainBtn.setSub_button(new Button[] { btn2.get(0),btn2.get(1) });break;
					case 1:mainBtn.setSub_button(new Button[] { btn1.get(0),btn2.get(0) });break;
					case 2:mainBtn.setSub_button(new Button[] { btn1.get(0),btn1.get(1) });break;
					default:break;
					}break;
				case 3:
					switch (btn1.size()) {
					case 0:mainBtn.setSub_button(new Button[] { btn2.get(0),btn2.get(1), btn2.get(2) });break;
					case 1:mainBtn.setSub_button(new Button[] { btn1.get(0),btn2.get(0), btn2.get(1) });break;
					case 2:mainBtn.setSub_button(new Button[] { btn1.get(0),btn1.get(1), btn2.get(0) });break;
					case 3:mainBtn.setSub_button(new Button[] { btn1.get(0),btn1.get(1), btn1.get(2) });break;
					default:break;
					}
					break;
				case 4:
					switch (btn1.size()) {
					case 0:mainBtn.setSub_button(new Button[] { btn2.get(0),btn2.get(1), btn2.get(2), btn2.get(3) });break;
					case 1:mainBtn.setSub_button(new Button[] { btn1.get(0),btn2.get(0), btn2.get(1), btn2.get(2) });break;
					case 2:mainBtn.setSub_button(new Button[] { btn1.get(0),btn1.get(1), btn2.get(0), btn2.get(1) });break;
					case 3:mainBtn.setSub_button(new Button[] { btn1.get(0),btn1.get(1), btn1.get(2), btn2.get(0) });break;
					case 4:mainBtn.setSub_button(new Button[] { btn1.get(0),btn1.get(1), btn1.get(2), btn1.get(3) });break;
					default:break;
					}break;
				case 5:
					switch (btn1.size()) {
					case 0:mainBtn.setSub_button(new Button[] { btn2.get(0),btn2.get(1), btn2.get(2), btn2.get(3),btn2.get(4) });break;
					case 1:mainBtn.setSub_button(new Button[] { btn1.get(0),btn2.get(0), btn2.get(1), btn2.get(2),btn2.get(3) });break;
					case 2:mainBtn.setSub_button(new Button[] { btn1.get(0),btn1.get(1), btn2.get(0), btn2.get(1),btn2.get(2) });break;
					case 3:mainBtn.setSub_button(new Button[] { btn1.get(0),btn1.get(1), btn1.get(2), btn2.get(0),btn2.get(1) });break;
					case 4:mainBtn.setSub_button(new Button[] { btn1.get(0),btn1.get(1), btn1.get(2), btn1.get(3),btn2.get(0) });break;
					case 5:mainBtn.setSub_button(new Button[] { btn1.get(0),btn1.get(1), btn1.get(2), btn1.get(3),btn1.get(4) });break;
					default:break;
					}
					mainBtn.setSub_button(new Button[] { btn1.get(0),btn1.get(1), btn1.get(2), btn1.get(3), btn1.get(4) });break;
				default:break;
				}
				btn_0.add(mainBtn);
			} else {
				// 如果一级菜单下面没有有二级菜单
				if (bean.getMenutype().equals("click")) {
					CommonButton btnbe = new CommonButton();
					btnbe.setName(bean.getMenuname());
					btnbe.setType(bean.getMenutype());
					btnbe.setKey(bean.getMenukey());
					btn_2.add(btnbe);
				} else if (bean.getMenutype().equals("view")) {
					ViewButton btnbe = new ViewButton();
					btnbe.setName(bean.getMenuname());
					btnbe.setType(bean.getMenutype());
					btnbe.setUrl(bean.getRebackcontext());
					btn_1.add(btnbe);
				}
			}
		}
		switch (btn_0.size() + btn_1.size() + btn_2.size()) {
		case 1:
			switch (btn_0.size()) {
			case 0:
				switch (btn_1.size()) {
				case 0: menu.setButton(new Button[] { btn_2.get(0) }); break;
				case 1: menu.setButton(new Button[] { btn_1.get(0) }); break;
				default: break;
				}break;
			case 1: menu.setButton(new Button[] { btn_0.get(0) }); break;
			default: break;
			}break;
		case 2:
			switch (btn_0.size()) {
			case 0:
				switch (btn_1.size()) {
				case 0:menu.setButton(new Button[] { btn_2.get(0), btn_2.get(1) });break;
				case 1:menu.setButton(new Button[] { btn_1.get(0), btn_2.get(0) });break;
				case 2:menu.setButton(new Button[] { btn_1.get(0), btn_1.get(1) });break;
				default:break;
				}break;
			case 1:
				switch (btn_1.size()) {
				case 0:menu.setButton(new Button[] { btn_0.get(0), btn_2.get(0) });break;
				case 1:menu.setButton(new Button[] { btn_0.get(0), btn_1.get(0) });break;
				default:break;
				}break;
			case 2:menu.setButton(new Button[] { btn_0.get(0), btn_0.get(1) });break;
			default:break;
			} break;
		case 3:
			switch (btn_0.size()) {
			case 0:
				switch (btn_1.size()) {
				case 0: menu.setButton(new Button[] { btn_2.get(0), btn_2.get(1), btn_2.get(2) }); break;
				case 1: menu.setButton(new Button[] { btn_1.get(0), btn_2.get(0), btn_2.get(1) }); break;
				case 2: menu.setButton(new Button[] { btn_1.get(0), btn_1.get(1), btn_2.get(0) }); break;
				case 3: menu.setButton(new Button[] { btn_1.get(0), btn_1.get(1), btn_1.get(2) }); break;
				default: break;
				} break;
			case 1:
				switch (btn_1.size()) {
				case 0: menu.setButton(new Button[] { btn_0.get(0), btn_2.get(0), btn_2.get(1) }); break;
				case 1: menu.setButton(new Button[] { btn_0.get(0), btn_1.get(0), btn_2.get(0) }); break;
				case 2: menu.setButton(new Button[] { btn_0.get(0), btn_1.get(0), btn_1.get(1) }); break;
				default: break;
				} break;
			case 2:
				switch (btn_1.size()) {
				case 0: menu.setButton(new Button[] { btn_0.get(0), btn_2.get(0) }); break;
				case 1: menu.setButton(new Button[] { btn_0.get(0), btn_1.get(0) }); break;
				default: break;
				} break;
			case 3: menu.setButton(new Button[] { btn_0.get(0), btn_0.get(1), btn_0.get(2) }); break;
			default: break;
			} break;
		default: break;
		}
		return menu;
	}

	/**
	 * 查询图文消息一级
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void queryGraphicMessageByFirst(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		List<Map<String,Object>> beans = menuDao.queryGraphicMessageByFirst(map);
		outputObject.setBeans(beans);
		outputObject.setreturnCode(Constants.ZERO);
		outputObject.setreturnMessage("成功");
	}

}

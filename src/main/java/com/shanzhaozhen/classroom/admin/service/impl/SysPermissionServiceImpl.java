package com.shanzhaozhen.classroom.admin.service.impl;

import com.shanzhaozhen.classroom.admin.repository.SysPermissionRepository;
import com.shanzhaozhen.classroom.admin.service.SysPermissionService;
import com.shanzhaozhen.classroom.bean.SysPermission;
import com.shanzhaozhen.classroom.common.SystemConst.MenuType;
import com.shanzhaozhen.classroom.utils.UserDetailsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class SysPermissionServiceImpl implements SysPermissionService {

    @Autowired
    private SysPermissionRepository sysPermissionRepository;

    public List<SysPermission> getMenu() {

        List<String> actions = UserDetailsUtils.getActionsByLoginUser();
        List<SysPermission> allMenus = this.sysPermissionRepository.findAll();

        List<SysPermission> rootMenus = new ArrayList<>();
        List<SysPermission> noRootList = new ArrayList<>();
        for(SysPermission menu : allMenus) {
            if(menu.getPid() == null) {
                rootMenus.add(menu);
            }
            if(menu.getPid() != null &&
                (actions.indexOf(menu.getPermissionName()) > -1) ||
                    (StringUtils.isEmpty(menu.getPermissionName()) && menu.getType() == MenuType.PATH.getValue())
            ) {
                noRootList.add(menu);
            }
        }

        getChildren(noRootList, allMenus);

        filterMenu(rootMenus);

        rootMenus.sort((Comparator.comparing(SysPermission::getPriority)));

        return rootMenus;
    }

    @Override
    public Map<String, Object> getAllMenu() {
        List<SysPermission> allMenus = this.sysPermissionRepository.findAll();

//        List<SysPermission> rootMenus = new ArrayList<>();
//        List<SysPermission> noRootList = new ArrayList<>();
//        for(SysPermission menu : allMenus) {
//            if(menu.getPid() == null) {
//                rootMenus.add(menu);
//            }
//            if(menu.getPid() != null) {
//                noRootList.add(menu);
//            }
//        }
//
//        getChildren(noRootList, allMenus);
//
//        rootMenus.sort((Comparator.comparing(SysPermission::getPriority)));
//
        Map<String, Object> map = new HashMap<>();

        map.put("rows", allMenus);
        map.put("total", allMenus.size());

        return map;

    }


    /**
     * 递归查找菜单的子节点
     * @param noRootList
     * @param children
     * @return
     */
    public List<SysPermission> getChildren(List<SysPermission> noRootList, List<SysPermission> children) {
        for(SysPermission child : children) {
            List<SysPermission> grandsons = new ArrayList<>();
            for (SysPermission noRoot : noRootList) {
                if(child.getId().equals(noRoot.getPid())) {
                    grandsons.add(noRoot);
                }
            }
            if(grandsons.size() > 0) {
                child.setChildren(getChildren(noRootList, grandsons));
            }
        }
        children.sort((Comparator.comparing(SysPermission::getPriority)));

        return children;
    }


    /**
     * 清除没有子节点的菜单
     * @param menus
     * @return
     */
    public List<SysPermission> filterMenu(List<SysPermission> menus) {

        Iterator<SysPermission> iterator = menus.iterator();

        SysPermission menu = null;
        while (iterator.hasNext()) {
            menu = iterator.next();
            if (menu.getType() == MenuType.PATH.getValue()) {
                if (menu.getChildren() != null && menu.getChildren().size() > 0) {
                    filterMenu(menu.getChildren());
                } else {
                    iterator.remove();
                }
            }
        }

        return menus;

    }

}

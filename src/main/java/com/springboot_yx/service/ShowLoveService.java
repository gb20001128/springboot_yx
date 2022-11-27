package com.springboot_yx.service;

import com.springboot_yx.bean.ShowLove;

import java.util.List;

public interface ShowLoveService {

    int addLove(ShowLove love);

    List<ShowLove> selectLoveALL();

    List<ShowLove> myHistory(String userName);

    int delete(String imageName);

    int updateShowLove(String oldName,String newName);

    //换头像
    int updateHeadImage(String userName,String headImage);

    String selectNameById(Integer id);

}

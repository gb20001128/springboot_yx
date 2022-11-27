package com.springboot_yx.service;

import com.springboot_yx.bean.Love;

import java.util.List;

public interface LoveService {


    int addLove(Love love);

    List<Love> selectLove(Love love);

    int updateLove(String oldName,String newName);

    int updateImage(String userName,String headImage);

    Love selectLoveByName(String name);

    Love selectLoveById(Integer id);

}

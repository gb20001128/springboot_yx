package com.springboot_yx.service;

import com.springboot_yx.bean.Love;
import com.springboot_yx.mapper.LoveMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoveServiceImpl implements LoveService{


    @Autowired
    LoveMapper loveMapper;

    @Override
    public int addLove(Love love) {
        return loveMapper.addLove(love);
    }

    @Override
    public List<Love> selectLove(Love love) {
        return loveMapper.selectLove(love);
    }

    @Override
    public int updateLove(String oldName,String newName){
        return loveMapper.updateLove(oldName,newName);
    }

    @Override
    public int updateImage(String userName, String headImage) {
        return loveMapper.updateImage(userName,headImage);
    }

    @Override
    public Love selectLoveByName(String name) {
        return loveMapper.selectLoveByName(name);
    }

    @Override
    public Love selectLoveById(Integer id) {
        return loveMapper.selectLoveById(id);
    }
}

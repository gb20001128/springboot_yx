package com.springboot_yx.service;

import com.springboot_yx.bean.ShowLove;
import com.springboot_yx.mapper.ShowLoveMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ShowLoveServiceIml implements ShowLoveService{

    @Autowired
    ShowLoveMapper showLoveMapper;

    @Override
    public int addLove(ShowLove love) {
        return showLoveMapper.addLove(love);
    }

    @Override
    public List<ShowLove> selectLoveALL() {
        return showLoveMapper.selectLoveALL();
    }

    @Override
    public List<ShowLove> myHistory(String userName){
        return showLoveMapper.myHistory(userName);
    }

    @Override
    public int delete(String imageName){
        return showLoveMapper.delete(imageName);
    }

    @Override
    public  int updateShowLove(String oldName,String newName){
        return  showLoveMapper.updateShowLove(oldName,newName);
    }

    @Override
    public int updateHeadImage(String userName, String headImage) {
        return showLoveMapper.updateHeadImage(userName,headImage);
    }

    @Override
    public String selectNameById(Integer id) {
        return showLoveMapper.selectNameById(id);
    }
}

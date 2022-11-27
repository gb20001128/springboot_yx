package com.springboot_yx.mapper;

import com.springboot_yx.bean.Love;
import com.springboot_yx.bean.ShowLove;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
 public interface ShowLoveMapper {

    int addLove(ShowLove love);

    List<ShowLove> selectLoveALL();

    List<ShowLove> myHistory(String userName);

    int delete(String imageName);

    int updateShowLove(String oldName,String newName);

    //换头像
    int updateHeadImage(String userName,String headImage);

    String selectNameById(Integer id);

}
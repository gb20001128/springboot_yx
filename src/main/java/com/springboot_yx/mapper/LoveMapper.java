package com.springboot_yx.mapper;

import com.springboot_yx.bean.Love;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LoveMapper {

    int addLove(Love love);

    List<Love> selectLove(Love love);

    int updateLove(String oldName,String newName);

    int updateImage(String userName,String headImage);

    Love selectLoveByName(String name);

    Love selectLoveById(Integer id);
}

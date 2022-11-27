package com.springboot_yx.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot_yx.bean.MicroVideo;
import com.springboot_yx.mapper.MicroVideoMapper;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author gb
 * @Data 2022/10/24 15:46
 */
@Service
public class MicroVideoServiceImpl extends ServiceImpl<MicroVideoMapper, MicroVideo> implements MicroVideoService {
}

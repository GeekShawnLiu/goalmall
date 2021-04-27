package www.tonghao.platform.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import tk.mybatis.mapper.entity.Example;
import www.tonghao.common.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.common.utils.StringUtil;
import www.tonghao.service.common.entity.PortalArea;
import www.tonghao.service.common.service.PortalAreaService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/portalArea")
@Api(value="PortalAreaController",description=" 首页专区Api")
public class PortalAreaController {

    @Autowired
    private PortalAreaService portalAreaService;

    @GetMapping(value = "/list")
    public List<PortalArea> getList(String areaName) {
        Example example = new Example(PortalArea.class);
        example.setOrderByClause(" area_sort asc");
        if (StringUtil.strIsNotEmpty(areaName)) {
            Example.Criteria criteria = example.createCriteria();
            criteria.andLike("areaName","%"+areaName+"%");
        }
        List<PortalArea> list = portalAreaService.selectByExample(example);
        return list;
    }

    @PostMapping("/saveOrUpdate")
    public Map<String,Object> saveOrUpdate(@RequestBody PortalArea portalArea) {
        return portalAreaService.saveOrUpdate(portalArea);
    }

    @DeleteMapping("/deleteById")
    public Map<String,Object> delete(Long id) {
        int edit = portalAreaService.delete(id);
        return ResultUtil.resultMessage(edit,"");
    }
}

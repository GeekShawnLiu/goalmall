package www.tonghao.platform.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.common.utils.PageBean;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.service.common.entity.PortalProduct;
import www.tonghao.service.common.service.PortalProductService;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/portalProduct")
@Api(value="portalProductController",description=" 首页商品Api")
public class PortalProductController {

    @Autowired
    private PortalProductService portalProductService;

    @GetMapping(value = "/getPage")
    public PageInfo<PortalProduct> getPage(@ModelAttribute PageBean page) {
        PageHelper.startPage(page.getPage(), page.getRows());
        List<PortalProduct> list = portalProductService.getAllByPosition();
        return new PageInfo<PortalProduct>(list);
    }

    @PostMapping(value = "/saveOrUpdate")
    public Map<String,Object> save(@RequestBody PortalProduct portalProduct) {
        int edit = 0;
        portalProduct.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
        if (portalProduct.getId() != null) {
            edit = portalProductService.updateNotNull(portalProduct);
        }else {
            Integer maxPosition = portalProductService.getMaxPosition();
            portalProduct.setPosition(maxPosition+1);
            portalProduct.setCreatedAt(DateUtilEx.timeFormat(new Date()));
            edit = portalProductService.save(portalProduct);
        }
        return ResultUtil.resultMessage(edit,"");
    }

    @DeleteMapping(value = "/deleteById")
    public Map<String,Object> deleteById(Long id){
        int edit = portalProductService.delete(id);
        return  ResultUtil.resultMessage(edit,"");
    }

    @GetMapping(value = "/positionUp")
    public Map<String,Object> positionUp(Long id,Long relationId){
        PortalProduct portalProduct = portalProductService.selectByKey(id);
        PortalProduct relationProduct = portalProductService.selectByKey(relationId);
        int edit = 0;
        if (portalProduct != null && relationProduct != null) {
            portalProduct.setPosition(portalProduct.getPosition()+1);
            relationProduct.setPosition(relationProduct.getPosition()-1);
            edit += portalProductService.updateNotNull(relationProduct);
            edit += portalProductService.updateNotNull(portalProduct);
        }
        return ResultUtil.resultMessage(edit,"");
    }

    @GetMapping(value = "/positionDown")
    public Map<String,Object> positionDown(Long id,Long relationId){
        PortalProduct portalProduct = portalProductService.selectByKey(id);
        PortalProduct relationProduct = portalProductService.selectByKey(relationId);
        int edit = 0;
        if (portalProduct != null && relationProduct != null) {
            portalProduct.setPosition(portalProduct.getPosition()-1);
            relationProduct.setPosition(relationProduct.getPosition()+1);
            edit += portalProductService.updateNotNull(relationProduct);
            edit += portalProductService.updateNotNull(portalProduct);
        }
        return ResultUtil.resultMessage(edit,"");
    }

}

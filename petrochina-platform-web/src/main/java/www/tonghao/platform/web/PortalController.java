package www.tonghao.platform.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;
import www.tonghao.common.warpper.TreeNode;
import www.tonghao.service.common.entity.PlatformCatalogs;
import www.tonghao.service.common.entity.PortalArea;
import www.tonghao.service.common.service.PlatformCatalogsService;
import www.tonghao.service.common.service.PortalAreaService;

import java.util.List;

@RestController
@RequestMapping("/portal")
@Api(value="portalController",description=" 首页Api")
public class PortalController  {

    @Autowired
    private PlatformCatalogsService platformCatalogsService;

    @Autowired
    private PortalAreaService portalAreaService;

    @GetMapping(value = "/getPortalCatalog")
    public List<TreeNode> getPortal(){
       return platformCatalogsService.getPortalCatalogs();
    }

    @GetMapping(value = "/portalArea")
    public List<PortalArea> getPortalArea() {
        return portalAreaService.getAllBySort();
    }

    @RequestMapping(value="/portalAreaCatalog",method={RequestMethod.GET,RequestMethod.POST})
    public List<PlatformCatalogs> getChildrenByLevel(Long id){
        List<PlatformCatalogs> list = null;
        Example example=new Example(PlatformCatalogs.class);
        Example.Criteria createCriteria = example.createCriteria();
        example.setOrderByClause("priority");
        createCriteria.andEqualTo("isDelete", 0);
        if(id != null){
            createCriteria.andEqualTo("parentId", id);
            list = platformCatalogsService.selectByExample(example);
            list.stream().forEach(e->{
                e.setIsParent("false");
            });
        }else{
            createCriteria.andEqualTo("treeDepth", 1);
            list = platformCatalogsService.selectByExample(example);
        }

        return list;
    }


}

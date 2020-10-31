package www.tonghao.platform.web;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import www.tonghao.common.warpper.TreeNode;
import www.tonghao.service.common.entity.PlatformCatalogs;
import www.tonghao.service.common.service.PlatformCatalogsService;

import java.util.List;

@RestController
@RequestMapping("/portal")
@Api(value="portalController",description=" 首页Api")
public class PortalController  {

    @Autowired
    private PlatformCatalogsService platformCatalogsService;

    @GetMapping(value = "/getPortalCatalog")
    public List<TreeNode> getPortal(){
       return platformCatalogsService.getPortalCatalogs();
    }
}

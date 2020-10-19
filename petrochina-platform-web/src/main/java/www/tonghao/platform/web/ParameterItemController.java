package www.tonghao.platform.web;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.platform.service.ParameterItemService;
import www.tonghao.service.common.entity.Parameter;
import www.tonghao.service.common.entity.ParameterItem;

import java.util.Map;

@RestController
@RequestMapping("/parameterItem")
@Api(value="parameterItemController",description="参数ItemApi")
public class ParameterItemController{
    @Autowired
    private ParameterItemService parameterItemService;

    @PostMapping(value = "/saveOrUpdate")
    public Map<String, Object> saveOrUpdate(@RequestBody ParameterItem item){
        return parameterItemService.saveOrUpdate(item);
    }

    @DeleteMapping(value = "/deleteById")
    public Map<String,Object> deleteById(Long id){
        int result = parameterItemService.delete(id);
        return ResultUtil.resultMessage(result,"");
    }

//    @DeleteMapping(value = "deleteByParameterId")
//    public Map<String,Object> deleteByParameterId(Long parameterId){
//        int result = parameterService.delete(id);
//        return ResultUtil.resultMessage(result,"");
//    }
}

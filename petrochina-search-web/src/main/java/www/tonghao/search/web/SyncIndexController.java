package www.tonghao.search.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import www.tonghao.service.common.entity.Products;
import www.tonghao.service.common.service.ProductSearchService;
import www.tonghao.service.common.service.ProductsService;

/**
 * 索引庫管理
 *
 * @author Easong
 * @create 2018-07-13 15:08
 **/
@Api(value="searchController",description="同步索引库api")
@RestController
@RequestMapping(value = "/syncIndex")
@Scope("singleton")
public class SyncIndexController {
	
	private static Log logger = LogFactory.getLog(SyncIndexController.class);
	
	@Autowired
	private ProductsService productsService;

	@Autowired
	private ProductSearchService productSearchService;

	/**
	 * 
	 * Description: 根据指定商品同步索引库
	 * 
	 * @data 2019年4月29日
	 * @param 
	 * @return
	 */
	@ApiOperation(value="根据指定商品同步索引库")
	@PostMapping("/syncProductById")
	public void syncProductById(Long id) {
		ExecutorService pool = Executors.newCachedThreadPool();
		pool.execute(new Runnable() {

			@Override
			public void run() {
				logger.info("根据指定商品同步索引库开始");
				Products product = productsService.getById(id);
				productSearchService.addData(product);
				logger.info("根据指定商品同步索引库结束");
			}
		});
	}

	/**
	 * 
	 * Description: 所有商品同步索引库
	 * 
	 * @data 2019年4月29日
	 * @param 
	 * @return
	 */
	@ApiOperation(value="所有商品同步索引库")
	@PostMapping("/syncIndexAllProducts")
	public void syncIndexAllProducts() {
		ExecutorService pool = Executors.newCachedThreadPool();
		pool.execute(new Runnable() {

			@Override
			public void run() {
				logger.info("同步索引库开始");
				List<Products> list = productsService.selectByExample(null);
				productSearchService.addBulkDatas(list);
				logger.info("同步索引库结束");
			}
		});
	}

	/**
	 * 
	 * Description: 创建索引库
	 * 
	 * @data 2019年4月29日
	 * @param 
	 * @return
	 */
	@ApiOperation(value="创建索引库")
	@PostMapping("/createIndexMapping")
	public void createIndexMapping() {
		ExecutorService pool = Executors.newCachedThreadPool();
		pool.execute(new Runnable() {

			@Override
			public void run() {
				logger.info("创建索引库");
				productSearchService.deleteIndex();
				productSearchService.createIndex();
				logger.info("创建索引库");
			}
		});
	}

	/**
	 * 
	 * Description: 删除索引库
	 * 
	 * @data 2019年4月29日
	 * @param 
	 * @return
	 */
	@ApiOperation(value="删除索引库")
	@DeleteMapping("/delIndexRepertory")
	public void delIndexRepertory() {
		ExecutorService pool = Executors.newCachedThreadPool();
		pool.execute(new Runnable() {

			@Override
			public void run() {
				logger.info("删除索引库开始");
				productSearchService.deleteIndex();
				logger.info("删除索引库结束");
			}
		});
	}
}

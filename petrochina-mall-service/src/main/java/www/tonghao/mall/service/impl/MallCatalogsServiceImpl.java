package www.tonghao.mall.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.tonghao.common.redis.RedisDao;
import www.tonghao.common.utils.ProductUtil;
import www.tonghao.common.utils.TreeBuilder;
import www.tonghao.common.warpper.TreeNode;
import www.tonghao.mall.entity.MallCatalogs;
import www.tonghao.mall.mapper.MallCatalogsMapper;
import www.tonghao.mall.service.MallCatalogsService;
import www.tonghao.service.common.base.impl.BaseServiceImpl;
import www.tonghao.service.common.entity.PlatformCatalogs;
import www.tonghao.service.common.mapper.PlatformCatalogsMapper;

import com.beust.jcommander.internal.Sets;
import com.google.common.collect.Lists;

@Service("mallCatalogsService")
public class MallCatalogsServiceImpl extends BaseServiceImpl<MallCatalogs> implements MallCatalogsService {

	@Autowired
	private MallCatalogsMapper mallCatalogsMapper;
	
	@Autowired
	private PlatformCatalogsMapper platformCatalogsMapper;
	
	@Autowired
	private RedisDao redisDao;
	
	@Override
	public MallCatalogs findById(Long id) {
		return mallCatalogsMapper.findById(id);
	}
	
	@Override
	public TreeNode findCatalogNode(Long platformCatalogId) {
		PlatformCatalogs platformCatalogs = platformCatalogsMapper.findRelationById(platformCatalogId);
		
		TreeNode pcNode = new TreeNode();
		pcNode.setId(platformCatalogs.getId());
		pcNode.setName(platformCatalogs.getName());
		pcNode.setLinkUrl("");
		pcNode.setExtStr("p-"+platformCatalogs.getId());//m表示平台品目ID
		Long mallCatalogId = platformCatalogs.getMallCatalogId();
		MallCatalogs mallCatalog = null;
		if(mallCatalogId!=null){
			mallCatalog = mallCatalogsMapper.findById(mallCatalogId);
			TreeNode pcParentNode = new TreeNode(mallCatalog.getId(),mallCatalog.getParentId(),mallCatalog.getName());
			pcNode.setParent(pcParentNode);
			pcNode.setExtStr("m-"+mallCatalog.getId());//m表示商城品目ID
			TreeNode parent = null;
			while (mallCatalog!=null) {
				mallCatalog = mallCatalog.getParent();
				if(mallCatalog!=null){
					TreeNode parentNode = new TreeNode(mallCatalog.getId(), mallCatalog.getParentId(), mallCatalog.getName());
					parentNode.setIcon(mallCatalog.getIcon());
					parentNode.setExtStr("m-"+mallCatalog.getId());
					if(pcParentNode.getParent()==null){
						parent = parentNode;
						pcParentNode.setParent(parent);
					}else{
						parent = pcParentNode.getParent();
						parent.setParent(parentNode);
					}
				}
			}
		}else{
			TreeNode parent = null;
			while (platformCatalogs!=null) {
				platformCatalogs = platformCatalogs.getParent();
				if(platformCatalogs!=null){
					TreeNode parentNode = new TreeNode(platformCatalogs.getId(), platformCatalogs.getParentId(), platformCatalogs.getName());
					parentNode.setIcon(platformCatalogs.getPic());
					parentNode.setLinkUrl("");
					parentNode.setExtStr("p-"+platformCatalogs.getId());
					if(pcNode.getParent()==null){
						parent = parentNode;
						pcNode.setParent(parent);
					}else{
						parent = pcNode.getParent();
						parent.setParent(parentNode);
					}
				}
			}
		}
		
		return pcNode;
	}

	@Override
	public List<TreeNode> getNodeList(Long platformCatalogId) {
		TreeNode node = findCatalogNode(platformCatalogId);
		LinkedList<TreeNode> nodeList = Lists.newLinkedList();
		while (node != null) {
			nodeList.addFirst(node);
			node = node.getParent();
		}
		return nodeList;
	}
	
	@Override
	public List<TreeNode> getCarNodeList(Long platformCatalogId) {
		PlatformCatalogs platformCatalogs = platformCatalogsMapper.findRelationById(platformCatalogId);
		LinkedList<TreeNode> nodeList = Lists.newLinkedList();
		if(platformCatalogs != null){
			TreeNode node = new TreeNode();
			node.setId(platformCatalogs.getId());
			node.setName(platformCatalogs.getName());
			node.setLinkUrl("");
			nodeList.addFirst(node);
			
			PlatformCatalogs parent = platformCatalogs.getParent();
			if(parent!=null){
				TreeNode parentNode = new TreeNode();
				parentNode.setId(parent.getId());
				parentNode.setName(parent.getName());
				parentNode.setLinkUrl("");
				nodeList.addFirst(parentNode);
			}
		}
		
		return nodeList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TreeNode> getTreeList(Integer count, String displayPlatform) {
		List<TreeNode> treeList = new ArrayList<>();
		Object value = redisDao.getValue("mall_catalogs_list");
		if(value != null){
			treeList = (List<TreeNode>)value;
		}else{
			treeList = getAllMallCatalogsTree();
			redisDao.setKey("mall_catalogs_list", treeList);
		}
		List<TreeNode> resultTree = new ArrayList<TreeNode>();
		if(!CollectionUtils.isEmpty(treeList)){
			for (TreeNode treeNode : treeList) {
				if(treeNode.getDisplayPlatform().contains(displayPlatform == null ? "pc" : displayPlatform)){
					resultTree.add(treeNode);
				}
			}
			if(count != null && resultTree.size()>count){
				resultTree = resultTree.subList(0, count);
			}
		}
		return resultTree;
	}

	/**
	 * 
	 * Description: 商城首页获取所有的品目
	 * 
	 * @data 2019年7月25日
	 * @param 
	 * @return
	 */
	private List<TreeNode> getAllMallCatalogsTree(){
		List<MallCatalogs> list = mallCatalogsMapper.findAllUsableList();
		List<TreeNode> treeList = Lists.newArrayListWithExpectedSize(list.size());
		TreeNode node = null;
		for(MallCatalogs mc:list){
			node = new TreeNode(mc.getId(), mc.getParentId() ,mc.getName(),mc.getPriority());
			node.setIcon(mc.getIcon());
			node.setMobileImg(mc.getMobileImg());
			node.setImgFormat(mc.getImgFormat());
			node.setExtStr("m-"+mc.getId());//m表示商城品目ID
			node.setDisplayPlatform(mc.getDisplayPlatform());
			//如果当前为二级目录
			if(mc.getTreeDepth()==1){
				node.setExistChildren(true);
				List<TreeNode> children = Lists.newArrayList();
				List<PlatformCatalogs> pclist = mc.getPlatformCatalogList();
				if(pclist!=null&&pclist.size()>0){
					for(PlatformCatalogs pc:pclist){
						TreeNode cpNode = new TreeNode();
						cpNode.setId(pc.getId());
						cpNode.setName(pc.getName());
						cpNode.setLinkUrl(ProductUtil.getMallCatalogUrl(pc.getId()));
						cpNode.setExtStr("p-"+pc.getId());//p表示商城品目ID
						cpNode.setParentId(node.getId());
						children.add(cpNode);
					}
					
					node.setChildren(children);
				}
				
			}
			treeList.add(node);
		}
		
		Collections.sort(treeList, (node1,node2) -> {
			 if(node1.getSort()!=null&&node2.getSort()!=null){
				 return (int) (node1.getSort()-node2.getSort());
			 }
			 return 0;
		});
		
		TreeBuilder tb = new TreeBuilder();
		return tb.listToTree(treeList);
	}
	
	
	@Override
	public List<TreeNode> getUsableNodeList() {
		List<MallCatalogs> list = mallCatalogsMapper.findAllUsableList();
		List<TreeNode> treeList = Lists.newArrayListWithExpectedSize(list.size());
		for(MallCatalogs mc:list){
			TreeNode node = new TreeNode(mc.getId(), mc.getParentId() ,mc.getName(),mc.getPriority());
			node.setIcon(mc.getIcon());
			treeList.add(node);
		}
		
		TreeBuilder tb = new TreeBuilder();
		return tb.listToTree(treeList);
	}

	@Override
	public List<TreeNode> getSupplierTreeList(Long supplierId) {
		List<MallCatalogs> catalogs = mallCatalogsMapper.getSupplierCatalogsList(supplierId);
	    List<TreeNode> result = Lists.newArrayList();
	    
	    Set<Long> unique = Sets.newHashSet();
	    for(MallCatalogs catalog:catalogs){
	    	List<MallCatalogs> nodeList = catalog.getNodeList();
	    	for(MallCatalogs nodeCatalog:nodeList){
	    		if(!unique.contains(nodeCatalog.getId())&&!nodeCatalog.equals(catalog)){
	    			unique.add(nodeCatalog.getId());
	    			result.add(new TreeNode(nodeCatalog.getId(),nodeCatalog.getParentId(),nodeCatalog.getName(),nodeCatalog.getPriority()));
	    		}
	    	}
	    	
			TreeNode mallNode = new TreeNode(catalog.getId(), catalog.getParentId(), catalog.getName(),catalog.getPriority());
			mallNode.setExistChildren(true);
			List<TreeNode> childs = Lists.newArrayList();
			List<PlatformCatalogs> platformCatalogs = catalog.getPlatformCatalogList();
			if(platformCatalogs!=null){
				platformCatalogs.forEach(pc -> {
					TreeNode child = new TreeNode();
					child.setId(pc.getId());
					child.setName(pc.getName());
					child.setLinkUrl("");
					childs.add(child);
				});
			}
			
			mallNode.setChildren(childs);
			result.add(mallNode);
	    }
		
		Collections.sort(result, (node1,node2) -> {
			 if(node1.getSort()!=null&&node2.getSort()!=null){
				 return (int) (node1.getSort()-node2.getSort());
			 }
			 return 0;
		});
		
		TreeBuilder tb = new TreeBuilder();
		result = tb.listToTree(result);
		return result;
	}

	@Override
	public List<TreeNode> getBiddingTreeList() {
		List<MallCatalogs> list = mallCatalogsMapper.findAllUsableBiddingList();
		List<TreeNode> treeList = Lists.newArrayListWithExpectedSize(list.size());
		
		TreeNode node = null;
		for(MallCatalogs mc:list){
			node = new TreeNode(mc.getId(), mc.getParentId() ,mc.getName(),mc.getPriority());
			node.setIcon(mc.getIcon());
			node.setExtStr("m-"+mc.getId());//m表示商城品目ID
			//如果当前为二级目录
			if(mc.getTreeDepth()==1){
				node.setExistChildren(true);
				List<TreeNode> children = Lists.newArrayList();
				List<PlatformCatalogs> pclist = mallCatalogsMapper.findBidingPlatformCatalogsByMcid(mc.getId());
				if(pclist!=null&&pclist.size()>0){
					for(PlatformCatalogs pc:pclist){
						TreeNode cpNode = new TreeNode();
						cpNode.setId(pc.getId());
						cpNode.setName(pc.getName());
						cpNode.setLinkUrl("");
						cpNode.setExtStr("p-"+pc.getId());//p表示商城品目ID
						cpNode.setParentId(node.getId());
						children.add(cpNode);
					}
					
					node.setChildren(children);
				}
				
			}
			treeList.add(node);
		}
		
		Collections.sort(treeList, (node1,node2) -> {
			 if(node1.getSort()!=null&&node2.getSort()!=null){
				 return (int) (node1.getSort()-node2.getSort());
			 }
			 return 0;
		});
		
		TreeBuilder tb = new TreeBuilder();
		treeList = tb.listToTree(treeList);
		return treeList;
	}

	@Override
	public List<MallCatalogs> getMallCatalogsByActivity(Map<String, Object> map) {
		return mallCatalogsMapper.getMallCatalogsByActivity(map);
	}

	@Override
	public List<MallCatalogs> selectMallCatalogsByProducts() {
		return mallCatalogsMapper.selectMallCatalogsByProducts();
	}

}

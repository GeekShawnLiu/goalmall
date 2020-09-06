package www.tonghao.common.utils;

import java.util.List;

import www.tonghao.common.warpper.TreeNode;

import com.google.common.collect.Lists;

/**
 * 树数据构造器
 * @author developer001
 *
 */
public class TreeBuilder {
	
	/**
	 * list转树结构
	 * @param list
	 * @return
	 */
	public List<TreeNode> listToTree(List<TreeNode> list){
		if(list!=null){
			for(TreeNode node:list){
				node.setChildren(getChildren(list, node));
			}
		}
		List<TreeNode> treeNodes = Lists.newArrayList();
		for(TreeNode node:list){
			if(node.getGrade()==0){
				treeNodes.add(node);
			}
		}
		return treeNodes;
	}
	
	/**
	 * 树转list
	 * @param tree
	 * @return
	 */
	public List<TreeNode> treeToList(List<TreeNode> tree){
		if(tree!=null&&tree.size()>0){
			List<TreeNode> allList = Lists.newArrayList();
			for(TreeNode node:tree){
				allList.add(node);
				recursiveAllChildren(allList, node);
			}
			return allList;
		}
		return tree;
	}
	
	/**
	 * 获取子节点
	 * @param list
	 * @param node
	 * @return
	 */
	public List<TreeNode> getChildren(List<TreeNode> list,TreeNode node){
		
		if(node.isExistChildren()&&node.getChildren()!=null){
			return node.getChildren();
		}
		
		//子节点
		List<TreeNode> children = Lists.newArrayList();
		for(int k=0;k<list.size();k++){
			TreeNode child = list.get(k);
			if(child.getParentId()!=null&&child.getParentId().equals(node.getId())){
				children.add(child);
			}
		}
		
		for(int k=0;k<children.size();k++){
			TreeNode child = children.get(k);
			child.setGrade(node.getGrade()+1);
			//递归
			child.setChildren(getChildren(list, child));
		}
		return children;
	}
	
		/**
		 * 递归子节点
		 * @param allchildren
		 * @param node
		 */
	 	private void recursiveAllChildren(List<TreeNode> allchildren, TreeNode node){
			List<TreeNode> children = node.getChildren();
			if(children!=null){
				for (TreeNode item : children) {
					allchildren.add(item);
					recursiveAllChildren(allchildren,item);
				}
			}
		}
}

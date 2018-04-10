package com.zlkj.ssm.shop.core;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static com.zlkj.ssm.shop.common.basic.Base.empty;

import com.zlkj.ssm.shop.core.cache.provider.SystemManager;
import com.zlkj.ssm.shop.core.page.PagerModel;

import java.util.List;
@Transactional(readOnly = true)
public abstract class ServersManager<E extends PagerModel, DAO extends  DaoManager<E>> implements Services<E>{
    @Autowired
    protected SystemManager systemManager;
	protected DAO dao;
	public DAO getDao() {
		return dao;
	}

	public abstract void setDao(DAO dao);

	/**
	 * 添加
	 * 
	 * @param e
	 * @return
	 */
	@Transactional(readOnly = false)
	public int insert(E e) {
		if(e==null){
			throw new NullPointerException();
		}
		return dao.insert(e);
	}


	/**
	 * 删除
	 * 
	 * @param e
	 * @return
	 */
	@Transactional(readOnly = false)
	public int delete(E e) {
		if(e==null){
			throw new NullPointerException();
		}
		return dao.delete(e);
	}

	/**
	 * 批量删除
	 * 
	 * @param ids
	 * @return
	 */
	@Transactional(readOnly = false)
	public int deletes(String[] ids) {
		if (ids == null || ids.length == 0) {
			throw new NullPointerException("id不能全为空！");
		}
		
		for (int i = 0; i < ids.length; i++) {
			if(empty(ids[i])){
				throw new NullPointerException("id不能为空！");
			}
			dao.deleteById(Integer.parseInt(ids[i]));
		}
		return 0;
	}

	/**
	 * 修改
	 * 
	 * @param e
	 * @return
	 */
	@Transactional(readOnly = false)
	public int update(E e) {
		if(e==null){
			throw new NullPointerException();
		}
		return dao.update(e);
	}

	/**
	 * 查询一条记录
	 * 
	 * @param e
	 * @return
	 */
	public E selectOne(E e) {
		return dao.selectOne(e);
	}

	/**
	 * 分页查询
	 * 
	 * @param e
	 * @return
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public PagerModel selectPageList(E e) {
		List list = dao.selectPageList(e);
		PagerModel pm = new PagerModel();
		pm.setList(list);
		Object oneC = dao.selectPageCount(e);
		if(oneC!=null){
			pm.setTotal(Integer.parseInt(oneC.toString()));
		}else{
			pm.setTotal(0);
		}
		return pm;
	}
	
	public List<E> selectList(E e) {
		return dao.selectList(e);
	}

	@Override
	public E selectById(String id) {
		return dao.selectById(id);
	}
}

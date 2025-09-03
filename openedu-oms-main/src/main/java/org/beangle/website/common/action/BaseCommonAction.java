package org.beangle.website.common.action;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.ems.config.service.ClassConfigService;
import org.beangle.ems.security.Group;
import org.beangle.ems.security.GroupMember;
import org.beangle.ems.security.User;
import org.beangle.ems.web.action.SecurityActionSupport;
import org.beangle.model.Entity;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.website.common.service.SendEmail;
import org.beangle.website.system.model.DictData;
import org.beangle.website.system.model.DictTree;
import org.beangle.website.system.model.SystemArgument;
import org.beangle.website.system.model.Znxx;
import org.beangle.website.system.service.DictDataService;
import org.beangle.website.system.service.SystemLogService;

import com.opensymphony.xwork2.ActionContext;

public class BaseCommonAction extends SecurityActionSupport {

	/**
	 * 系统日志接口
	 */
	@Resource
	protected SystemLogService systemLogService;

	@Resource
	protected DictDataService dictDataService;

	@Resource
	protected ClassConfigService classConfigService;

	// 下载方法
	private int outPutBufferedSize = 1024;

	public void setClassConfigService(ClassConfigService classConfigService) {
		this.classConfigService = classConfigService;
	}

	public int getOutPutBufferedSize() {
		return outPutBufferedSize;
	}

	public void setOutPutBufferedSize(int outPutBufferedSize) {
		this.outPutBufferedSize = outPutBufferedSize;
	}

	public void setSystemLogService(SystemLogService systemLogService) {
		this.systemLogService = systemLogService;
	}

	public void setDictDataService(DictDataService dictDataService) {
		this.dictDataService = dictDataService;
	}

	public DictDataService getDictDataService() {
		return dictDataService;
	}

	/**
	 * 获取用户当前身份
	 * 
	 * @return
	 */
	protected Long getCurrentCategoryId() {
		Long curCategoryId = (Long) ActionContext.getContext().getSession().get("security.categoryId");
		return curCategoryId;
	}

	/**
	 * 是否是站群管理员身份
	 * 
	 * @return
	 */
	protected boolean isAllSitesAdmin() {
		// Long curCategoryId = getCurrentCategoryId();
		// if (curCategoryId != null && curCategoryId.equals(3L)) {
		if (isAdmin()) {
			return true;
		}
		return false;
	}

	/**
	 * 是否是站点管理员身份
	 * 
	 * @return
	 */
	protected boolean isOneSiteAdmin() {
		Long curCategoryId = getCurrentCategoryId();
		if (curCategoryId != null && curCategoryId.equals(2L)) {
			return true;
		}
		return false;
	}

	/**
	 * 是否是普通用户身份
	 * 
	 * @return
	 */
	protected boolean isGeneralUser() {
		Long curCategoryId = getCurrentCategoryId();
		if (curCategoryId != null && curCategoryId.equals(1L)) {
			return true;
		}
		return false;
	}
	
	public boolean isDeptAdmin(){
		Group group = entityDao.get(Group.class,3079L);
		if(getUserGroups().contains(group)){
			return true;
		}
		return false;
	}

	/**
	 * 获取用户包含的用户组
	 * 
	 * @param user
	 *            用户，如果为空取当前用户的用户组
	 * @return
	 */
	protected Set<Group> getUserGroups(User user) {
		if (user == null) {
			user = getCurrentUser();
		}
		if (user == null) {
			return null;
		}
		Set<Group> groups = CollectUtils.newHashSet();
		for (Iterator<GroupMember> it = user.getGroups().iterator(); it.hasNext();) {
			GroupMember gm = it.next();
			if (gm.isMember()) {
				groups.add(gm.getGroup());
			}
		}
		return groups;
	}

	/**
	 * 获取用户包含的用户组
	 * 
	 * @param user
	 *            用户，如果为空取当前用户的用户组
	 * @return
	 */
	protected Set<Group> getUserGroups() {
		return getUserGroups(getCurrentUser());
	}

	/**
	 * 根据字典类型ID查询字典数据
	 * 
	 * @param typeId
	 *            字典类型ID
	 * @return
	 */
	protected List<DictData> getDictDataByDictType(Long typeId) {
		OqlBuilder<DictData> query = OqlBuilder.from(DictData.class, "d");
		query.where("d.enabled=1");
		query.where("d.dictType.id=:dictType", typeId);
		query.orderBy("d.code");
		return entityDao.search(query);
	}

	/**
	 * 根据字典类型CODE查询字典数据
	 * 
	 * @param code
	 *            字典类型code
	 * @return
	 */
	protected List<DictData> getDictDataByDictTypeCode(String code) {
		OqlBuilder<DictData> query = OqlBuilder.from(DictData.class, "d");
		query.where("d.enabled=1");
		query.where("d.dictType.code=:dictType", code);
		query.orderBy("d.code");
		return entityDao.search(query);
	}

	/**
	 * 根据字典数据编码获得一个数据字典
	 * 
	 * @param code
	 * @return
	 */
	protected DictData getDictData(String code) {
		return dictDataService.getDictData(code);
	}

	/**
	 * 根据字典数据类型编码获得数据字典
	 * 
	 * @param code
	 * @return
	 */
	protected List<DictData> findDictData(String code) {
		return dictDataService.findDictData(code);
	}

	/**
	 * 获取系统参数
	 * 
	 * @return
	 */
	protected SystemArgument getSysteArgument() {
		return entityDao.get(SystemArgument.class, 1L);
	}

	/**
	 * 批量删除（规则：每次删除100条记录）
	 * 
	 * @param entityClass
	 *            要删除记录的实体
	 * @param map
	 *            条件
	 * @param id
	 *            要删除的记录的ID
	 */
	protected void plRemove(Class<?> entityClass, Map<Object, Object> map, List<?> id) {
		if (CollectionUtils.isNotEmpty(id)) {
			int count = 100;
			Map<Object, Object> parameterMap = new HashMap<Object, Object>();
			Object[] ids = id.toArray();
			parameterMap = getParameterMap(map);
			for (int i = 0; i < ids.length; i = i + count) {
				int length = ids.length > i + count ? count : ids.length - i;
				Long[] dids = new Long[length];
				System.arraycopy(ids, i, dids, 0, length);
				// entityService.remove(Salary.class, "id", dids);

				parameterMap.put("id", dids);
				entityDao.remove(entityClass, parameterMap);
			}
		}
	}

	/**
	 * 分解map
	 * 
	 * @param keyMap
	 * @return
	 */
	private Map<Object, Object> getParameterMap(Map<Object, Object> keyMap) {
		if (keyMap == null) {
			return new HashMap<Object, Object>();
		}
		Set<Object> keySet = keyMap.keySet();
		Map<Object, Object> params = new HashMap<Object, Object>();
		for (Iterator<Object> ite = keySet.iterator(); ite.hasNext();) {
			String keyName = ite.next().toString();
			Object keyValue = keyMap.get(keyName);
			String paramName = keyName.replace('.', '_');
			params.put(paramName, keyValue);
		}
		return params;
	}

	/**
	 * 判断其他实体是否存在相同属性
	 * 
	 * @param entity
	 *            实体
	 * @param keyName
	 *            字段名
	 * @param value
	 *            字段值
	 * @return
	 */
	protected boolean exist(Entity<?> entity, String keyName, String value) {
		OqlBuilder<?> oql = OqlBuilder.from(entity.getClass().getName(), "o");
		oql.where("o." + keyName + " = :keyvalue", value);
		if (entity.getIdentifier() != null) {
			oql.where("o.id <> " + entity.getIdentifier());
		}
		List<?> rs = entityDao.search(oql);
		if (rs.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 发送邮件
	 * 
	 * @param mail
	 *            邮件地址（支持多个，用;分割）
	 * @param title
	 *            邮件主题
	 * @param content
	 *            邮件内容
	 */
	protected void sendEmail(String mail, String title, String content) {
		SystemArgument sa = getSysteArgument();
		if (sa != null) {
			String hostName = sa.getYjfwqym();
			String mailUser = sa.getYjfwqyhm();
			String mailPwd = sa.getYjfwqmm();
			String sendMailr = sa.getYjfsr();
			// 发送邮件
			if (StringUtils.isNotEmpty(hostName) && StringUtils.isNotEmpty(mailUser) && StringUtils.isNotEmpty(mailPwd)
					&& StringUtils.isNotEmpty(sendMailr)) {
				SendEmail sendMail = new SendEmail();
				sendMail.setHost(hostName);
				sendMail.setUserName(mailUser);
				sendMail.setPassword(mailPwd);
				sendMail.setSenderAddr(sendMailr);
				sendMail.sendMessage(mail, title, content);
			}

		}
	}

	/**
	 * 发送站内消息
	 * 
	 * @param users
	 *            接收人
	 * @param title
	 *            标题
	 * @param content
	 *            内容
	 */
	protected void sendZnxx(Set<User> users, String title, String content) {
		Znxx znxx = new Znxx();
		znxx.setTitle(title);
		znxx.setContent(content);
		znxx.setReceives(users);
		znxx.setSender(getCurrentUser());
		znxx.setTime(new Date());
		entityDao.saveOrUpdate(znxx);
	}

	/**
	 * 查询二级字典树
	 * 
	 * @param ident
	 *            一级节点标识
	 * @return
	 */
	protected List<DictTree> getDictTrees(String ident) {
		OqlBuilder<DictTree> query = OqlBuilder.from(DictTree.class, "d");
		query.where("d.parent.dm=:dm", ident);
		query.where("d.parent.parent is null");
		query.where("d.enabled=1");
		query.orderBy("d.code");
		return entityDao.search(query);
	}

	/**
	 * 根据代码获取字典树节点
	 * 
	 * @param dm
	 *            代码
	 * @return
	 */
	protected DictTree getDictTreeByDM(String dm) {
		OqlBuilder<DictTree> query = OqlBuilder.from(DictTree.class, "d");
		query.where("d.dm=:dm", dm);
		List<DictTree> dictTrees = entityDao.search(query);
		DictTree dictTree = null;
		if (dictTrees != null && dictTrees.size() > 0) {
			dictTree = dictTrees.get(0);
		}
		return dictTree;
	}

	/**
	 * 根据父节点代码查找子节点
	 * 
	 * @param parentDM
	 *            父节点代码
	 * @return
	 */
	protected List<DictTree> findChildDictTrees(String parentDM) {
		DictTree dictTree = getDictTreeByDM(parentDM);
		if (dictTree != null) {
			OqlBuilder<DictTree> query = OqlBuilder.from(DictTree.class, "d");
			query.where("d.code like '" + dictTree.getCode() + "%'");
			query.where("d.enabled=1");
			query.orderBy("d.code");
			return entityDao.search(query);
		} else {
			return null;
		}
	}

	/**
	 * 下载导入模板
	 * 
	 * @param filePath
	 *            文件路径
	 * @param fileName
	 *            文件名
	 * @throws IOException
	 */
	protected void downImporterXlsFile(String filePath, String fileName) throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		String url = request.getSession().getServletContext().getRealPath(filePath);

		java.io.File file = new java.io.File(url);
		InputStream in = null;
		OutputStream out = null;
		if (file.exists()) {
			// response.setHeader("Content-Length", file.length() + "");]
			response.setHeader("Content-disposition", "attachment; filename=\"" + URLEncoder.encode(fileName, "UTF-8") + "\"");

			try {
				in = new FileInputStream(file);
				out = response.getOutputStream();
				byte[] bufferd = new byte[getOutPutBufferedSize()];
				while (in.read(bufferd) > 0) {
					out.write(bufferd);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	protected String getParamsStr() {
		String params = get("params");
		if (StringUtils.isEmpty(params)) {
			return "";
		}
		if ("null".equals(params)) {
			return null;
		}
		return "&params=" + URLEncoder.encode(params);
	}

	@SuppressWarnings("deprecation")
	protected void putParams() {
		String params = get("params");
		if (StringUtils.isEmpty(params)) {
			return;
		}
		params = URLDecoder.decode(params);
		put("params", params);
	}

	/**
	 * 查找所有有效的用户
	 * 
	 * @return
	 */
	protected List<User> findAllActivationUser() {
		OqlBuilder<User> query = OqlBuilder.from(User.class, "u");
		query.where("u.enabled=1");
		query.orderBy("u.name");
		return entityDao.search(query);
	}

	public boolean isAjax() {
		if ("XMLHttpRequest".equals(getRequest().getHeader("x-requested-with")) || StringUtils.isNotEmpty(get("x-requested-with"))) {
			return true;
		}
		return false;
	}

	@Override
	public String search() {
		//查询列表配置
//		put("listConfig", classConfigService.findConfig(getEntityClass(), ClassConfigService.LIST));
		return super.search();
	}

	@Override
	public String edit() {
		//查询表单配置
//		put("formConfig", classConfigService.findConfig(getEntityClass(), ClassConfigService.FORM));
		return super.edit();
	}

}

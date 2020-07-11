package org.sagacity.sqltoy.plugins.datasource.impl;

import java.util.Map;

import javax.sql.DataSource;

import org.sagacity.sqltoy.plugins.datasource.AbstractGetDataSource;
import org.sagacity.sqltoy.utils.StringUtil;
import org.springframework.context.ApplicationContext;

public class DefaultGetDataSource extends AbstractGetDataSource {

	@Override
	public DataSource getDataSource(ApplicationContext applicationContext, String defaultDataSource) {
		if (this.dataSource != null) {
			return this.dataSource;
		}
		Map<String, DataSource> result = applicationContext.getBeansOfType(DataSource.class);
		// 只有一个dataSource,直接使用
		if (result.size() == 1) {
			this.dataSource = result.values().iterator().next();
		}
		// 通过sqltoyContext中定义的默认dataSource来获取
		if (this.dataSource == null) {
			this.dataSource = (DataSource) applicationContext
					.getBean(StringUtil.isNotBlank(defaultDataSource) ? defaultDataSource : "dataSource");
		}
		return this.dataSource;
	}

}

package com.cloulu.samples;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class ReferenceDataRepository {
	
	private JdbcTemplate jdbcTemplate;
	
	@Inject
	public void init(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public String getDbInfo() {
		DataSource dataSource = jdbcTemplate.getDataSource();
		if (dataSource instanceof BasicDataSource) {
			return ((BasicDataSource) dataSource).getUrl();
		}
		else if (dataSource instanceof SimpleDriverDataSource) {
			return ((SimpleDriverDataSource) dataSource).getUrl();
		}
		return dataSource.toString();
	}
	
	public List<State> findAll() {
		return this.jdbcTemplate.query("select * from current_states", new RowMapper<State>() {
				public State mapRow(ResultSet rs, int rowNum) throws SQLException {
					State s = new State();
					s.setId(rs.getLong("id"));
					s.setStateCode(rs.getString("state_code"));
					s.setName(rs.getString("name"));
					return s;
				}
			});
	}
	
	public String getSystemProperties(){
		StringBuilder sb = new StringBuilder();
		for(Object key : System.getProperties().keySet()){
			 sb.append(key + " = " + System.getProperty(key.toString())+"\n");
		}
		System.out.println("*** System Properties ***");
		System.out.println(sb.toString());
		return sb.toString();
	}
	
	public String getEnvProperties(){
		StringBuilder sb = new StringBuilder();
		for(Object key : System.getenv().keySet()){
			 sb.append(key + " = " + System.getenv(key.toString())+"\n");
		}
		System.out.println("*** Enviorment Properties ***");
		System.out.println(sb.toString());
		return sb.toString();
	}


}

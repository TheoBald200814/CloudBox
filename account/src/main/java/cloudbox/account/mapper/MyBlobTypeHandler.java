//package cloudbox.account.mapper;
/**
 * 该类继承自Mybatis-plus中的BaseTypeHandler类，
 * 用于处理数据库和java中的数据类型映射关系，以下以Blob为例。
 * （注：在调用BaseMapper中的常规SQL方法时，Blob能够被自动映射，仅当使用@Results自定义特殊方法时，需要配置）*/
//import org.apache.ibatis.type.BaseTypeHandler;
//import org.apache.ibatis.type.JdbcType;
//
//import java.sql.*;
//
//public class MyBlobTypeHandler extends BaseTypeHandler<Blob> {
//    @Override
//    public void setNonNullParameter(PreparedStatement ps, int i, Blob parameter, JdbcType jdbcType) throws SQLException {
//        ps.setBlob(i, parameter);
//    }
//
//    @Override
//    public Blob getNullableResult(ResultSet rs, String columnName) throws SQLException {
//        return rs.getBlob(columnName);
//    }
//
//    @Override
//    public Blob getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
//        return rs.getBlob(columnIndex);
//    }
//
//    @Override
//    public Blob getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
//        return cs.getBlob(columnIndex);
//    }
//}


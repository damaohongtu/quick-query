package com.damaohongtu.orderquery.dao.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Node implements Serializable {
    private Long id;

    private String nodeCode;

    private String graphCode;

    private String nodeName;

    private String nodeType;

    private String dataSource;

    private String inputField;

    private String routeRule;

    private String neighborNode;

    private String coordinate;

    private Date createTime;

    private Date updateTime;

    private String createBy;

    private String updateBy;

    private Byte valid;

    private String outputField;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNodeCode() {
        return nodeCode;
    }

    public void setNodeCode(String nodeCode) {
        this.nodeCode = nodeCode == null ? null : nodeCode.trim();
    }

    public String getGraphCode() {
        return graphCode;
    }

    public void setGraphCode(String graphCode) {
        this.graphCode = graphCode == null ? null : graphCode.trim();
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName == null ? null : nodeName.trim();
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType == null ? null : nodeType.trim();
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource == null ? null : dataSource.trim();
    }

    public String getInputField() {
        return inputField;
    }

    public void setInputField(String inputField) {
        this.inputField = inputField == null ? null : inputField.trim();
    }

    public String getRouteRule() {
        return routeRule;
    }

    public void setRouteRule(String routeRule) {
        this.routeRule = routeRule == null ? null : routeRule.trim();
    }

    public String getNeighborNode() {
        return neighborNode;
    }

    public void setNeighborNode(String neighborNode) {
        this.neighborNode = neighborNode == null ? null : neighborNode.trim();
    }

    public String getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate == null ? null : coordinate.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    public Byte getValid() {
        return valid;
    }

    public void setValid(Byte valid) {
        this.valid = valid;
    }

    public String getOutputField() {
        return outputField;
    }

    public void setOutputField(String outputField) {
        this.outputField = outputField == null ? null : outputField.trim();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Node other = (Node) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getNodeCode() == null ? other.getNodeCode() == null : this.getNodeCode().equals(other.getNodeCode()))
            && (this.getGraphCode() == null ? other.getGraphCode() == null : this.getGraphCode().equals(other.getGraphCode()))
            && (this.getNodeName() == null ? other.getNodeName() == null : this.getNodeName().equals(other.getNodeName()))
            && (this.getNodeType() == null ? other.getNodeType() == null : this.getNodeType().equals(other.getNodeType()))
            && (this.getDataSource() == null ? other.getDataSource() == null : this.getDataSource().equals(other.getDataSource()))
            && (this.getInputField() == null ? other.getInputField() == null : this.getInputField().equals(other.getInputField()))
            && (this.getRouteRule() == null ? other.getRouteRule() == null : this.getRouteRule().equals(other.getRouteRule()))
            && (this.getNeighborNode() == null ? other.getNeighborNode() == null : this.getNeighborNode().equals(other.getNeighborNode()))
            && (this.getCoordinate() == null ? other.getCoordinate() == null : this.getCoordinate().equals(other.getCoordinate()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getCreateBy() == null ? other.getCreateBy() == null : this.getCreateBy().equals(other.getCreateBy()))
            && (this.getUpdateBy() == null ? other.getUpdateBy() == null : this.getUpdateBy().equals(other.getUpdateBy()))
            && (this.getValid() == null ? other.getValid() == null : this.getValid().equals(other.getValid()))
            && (this.getOutputField() == null ? other.getOutputField() == null : this.getOutputField().equals(other.getOutputField()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getNodeCode() == null) ? 0 : getNodeCode().hashCode());
        result = prime * result + ((getGraphCode() == null) ? 0 : getGraphCode().hashCode());
        result = prime * result + ((getNodeName() == null) ? 0 : getNodeName().hashCode());
        result = prime * result + ((getNodeType() == null) ? 0 : getNodeType().hashCode());
        result = prime * result + ((getDataSource() == null) ? 0 : getDataSource().hashCode());
        result = prime * result + ((getInputField() == null) ? 0 : getInputField().hashCode());
        result = prime * result + ((getRouteRule() == null) ? 0 : getRouteRule().hashCode());
        result = prime * result + ((getNeighborNode() == null) ? 0 : getNeighborNode().hashCode());
        result = prime * result + ((getCoordinate() == null) ? 0 : getCoordinate().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getCreateBy() == null) ? 0 : getCreateBy().hashCode());
        result = prime * result + ((getUpdateBy() == null) ? 0 : getUpdateBy().hashCode());
        result = prime * result + ((getValid() == null) ? 0 : getValid().hashCode());
        result = prime * result + ((getOutputField() == null) ? 0 : getOutputField().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", nodeCode=").append(nodeCode);
        sb.append(", graphCode=").append(graphCode);
        sb.append(", nodeName=").append(nodeName);
        sb.append(", nodeType=").append(nodeType);
        sb.append(", dataSource=").append(dataSource);
        sb.append(", inputField=").append(inputField);
        sb.append(", routeRule=").append(routeRule);
        sb.append(", neighborNode=").append(neighborNode);
        sb.append(", coordinate=").append(coordinate);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", createBy=").append(createBy);
        sb.append(", updateBy=").append(updateBy);
        sb.append(", valid=").append(valid);
        sb.append(", outputField=").append(outputField);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    public enum Column {
        id("id", "id", "BIGINT", false),
        nodeCode("node_code", "nodeCode", "VARCHAR", false),
        graphCode("graph_code", "graphCode", "VARCHAR", false),
        nodeName("node_name", "nodeName", "VARCHAR", false),
        nodeType("node_type", "nodeType", "VARCHAR", false),
        dataSource("data_source", "dataSource", "VARCHAR", false),
        inputField("input_field", "inputField", "VARCHAR", false),
        routeRule("route_rule", "routeRule", "VARCHAR", false),
        neighborNode("neighbor_node", "neighborNode", "VARCHAR", false),
        coordinate("coordinate", "coordinate", "VARCHAR", false),
        createTime("create_time", "createTime", "TIMESTAMP", false),
        updateTime("update_time", "updateTime", "TIMESTAMP", false),
        createBy("create_by", "createBy", "VARCHAR", false),
        updateBy("update_by", "updateBy", "VARCHAR", false),
        valid("valid", "valid", "TINYINT", false),
        outputField("output_field", "outputField", "LONGVARCHAR", false);

        private static final String BEGINNING_DELIMITER = "\"";

        private static final String ENDING_DELIMITER = "\"";

        private final String column;

        private final boolean isColumnNameDelimited;

        private final String javaProperty;

        private final String jdbcType;

        public String value() {
            return this.column;
        }

        public String getValue() {
            return this.column;
        }

        public String getJavaProperty() {
            return this.javaProperty;
        }

        public String getJdbcType() {
            return this.jdbcType;
        }

        Column(String column, String javaProperty, String jdbcType, boolean isColumnNameDelimited) {
            this.column = column;
            this.javaProperty = javaProperty;
            this.jdbcType = jdbcType;
            this.isColumnNameDelimited = isColumnNameDelimited;
        }

        public String desc() {
            return this.getEscapedColumnName() + " DESC";
        }

        public String asc() {
            return this.getEscapedColumnName() + " ASC";
        }

        public static Column[] excludes(Column ... excludes) {
            ArrayList<Column> columns = new ArrayList<>(Arrays.asList(Column.values()));
            if (excludes != null && excludes.length > 0) {
                columns.removeAll(new ArrayList<>(Arrays.asList(excludes)));
            }
            return columns.toArray(new Column[]{});
        }

        public static Column[] all() {
            return Column.values();
        }

        public String getEscapedColumnName() {
            if (this.isColumnNameDelimited) {
                return new StringBuilder().append(BEGINNING_DELIMITER).append(this.column).append(ENDING_DELIMITER).toString();
            } else {
                return this.column;
            }
        }

        public String getAliasedEscapedColumnName() {
            return this.getEscapedColumnName();
        }
    }
}
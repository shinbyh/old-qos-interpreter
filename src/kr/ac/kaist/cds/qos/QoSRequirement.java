package kr.ac.kaist.cds.qos;

import org.json.JSONException;
import org.json.JSONObject;

public class QoSRequirement {
	String metricName;
	String unit;
	double value;
	Operator operator;
	
	public QoSRequirement(String metricName, String unit, double value, Operator operator) {
		super();
		this.metricName = metricName;
		this.unit = unit;
		this.value = value;
		this.operator = operator;
	}

	public String getMetricName() {
		return metricName;
	}

	public void setMetricName(String metricName) {
		this.metricName = metricName;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public Operator getOperator() {
		return operator;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	public JSONObject toJSONObject(){
		JSONObject json = new JSONObject();
		
		try {
			json.put("metricName", this.metricName);
			json.put("metricUnit", this.unit);
			json.put("metricValue", this.value);
			json.put("metricOperator", this.operator);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return json;
	}
	
	public static QoSRequirement fromJSONObject(JSONObject json) throws JSONException{
		String metricName = json.getString("metricName");
		String unit = json.getString("metricUnit");
		double value = json.getDouble("metricValue");
		Operator operator = Operator.fromString(json.getString("metricOperator"));
		
		return new QoSRequirement(metricName, unit, value, operator);
	}
}

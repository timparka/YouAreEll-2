package models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/*
 * POJO for an Message object
 *
 *   {
    "sequence": "-",
    "timestamp": "_",
    "fromid": "xt0fer",
    "toid": "kristofer",
    "message": "Hello, Kristofer!"
  },

*
 */
public class Message implements Comparable {

    private String message = "";
    private String toId = "";
    private String fromId = "";
    private String timestamp = "2023-04-09T03:47:15.048818328Z";
    private String seqId = "-";

    @JsonCreator
    public Message(@JsonProperty("message") String message,
              @JsonProperty("toid") String toId,
              @JsonProperty("fromid") String fromId,
              @JsonProperty("timestamp") String timestamp,
                   @JsonProperty("sequence") String seqId) {
        this.message = message;
        this.toId = toId;
        this.fromId = fromId;
        this.timestamp = timestamp;
        this.seqId = seqId;
    }

    @Override
    public String toString() {
        return "To: " + this.toId + "\nFrom: "+ this.fromId + "\n" + this.message + "\n----\n";
    }

    public int compareTo(Object o) {
        return this.seqId.compareTo(((Message) o).getSeqId());
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getSeqId() {
        return seqId;
    }
}
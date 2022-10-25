package cz.speedy11.ukrainenewsnotifier.object;

import net.pushover.client.MessagePriority;

public record Report(String serviceId, String id, String text, long timestamp, MessagePriority priority) {
}
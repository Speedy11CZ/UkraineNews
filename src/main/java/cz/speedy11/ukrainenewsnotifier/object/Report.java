package cz.speedy11.ukrainenewsnotifier.object;

import net.pushover.client.MessagePriority;

/**
 * Object for storing report data.
 *
 * @param serviceId service ID
 * @param id report ID
 * @param text report text
 * @param timestamp report timestamp
 * @param priority report priority for Pushover
 */
public record Report(String serviceId, String id, String text, long timestamp, MessagePriority priority) {
}
package hei.school.demo.endpoint.event.consumer.model;

import hei.school.demo.PojaGenerated;
import hei.school.demo.endpoint.event.model.PojaEvent;

@PojaGenerated
public record TypedEvent(String typeName, PojaEvent payload) {}

DELETE FROM directus_relations WHERE collection = 'reservation' AND field IN ('item_id', 'item2', 'item_id2', 'profile_id', 'status_id');
INSERT INTO directus_relations (
    collection, 
    field, 
    related_collection, 
    meta, 
    schema
) VALUES (
    'reservation',
    'item_id',
    'item',
    '{"one_field": null, "one_collection_field": null, "one_allowed_collections": null, "junction_field": null, "sort_field": null, "one_deselect_action": "nullify"}',
    '{"table": "reservation", "column": "item_id", "foreign_key_table": "item", "foreign_key_column": "id", "constraint_name": "fk_reservation_item", "on_update": "NO ACTION", "on_delete": "RESTRICT"}'
);
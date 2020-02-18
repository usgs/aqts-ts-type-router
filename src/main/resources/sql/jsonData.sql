select json_data_id,
       response_code,
       (regexp_match(url, 'V2/(.*)\?'))[1] service_name,
       script_name,
       length(json_content::text) content_length
  from json_data
 where json_data_id = ?

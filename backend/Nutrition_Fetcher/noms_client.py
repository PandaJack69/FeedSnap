import noms

# Load API key
key = open('myapikey.txt', 'r').read()
client = noms.Client(key)

def get_nutrient_report(food_name: str, amount_g: int = 100):
    # Step 1: Search for the food
    search_results = client.search_query(food_name)
    
    # Access the JSON data stored in the SearchResults object
    if search_results.json is None or 'items' not in search_results.json or not search_results.json['items']:
        print(f"No results found for '{food_name}'")
        return None
    
    items = search_results.json['items']
    
    # Step 2: Select the best result (prioritize Foundation data type)
    selected_result = None
    for item in items:
        if item.get('dataType') == "Foundation":
            selected_result = item
            break
    
    if not selected_result:
        selected_result = items[0]  # Fallback to first result
    
    description = selected_result.get('description', 'Unknown Food')
    data_type = selected_result.get('dataType', 'Unknown Type')
    food_id = selected_result.get('fdcId')  # Note: ID is stored as 'fdcId'
    
    if not food_id:
        print("Selected food item does not have an ID")
        return None
        
    print(f"Selected: {description} ({data_type}, ID: {food_id})")
    
    # Step 3: Get nutrients for the selected food ID
    try:
        food_dict = {food_id: amount_g}
        food_objs = client.get_foods(food_dict)
        meal = noms.Meal(food_objs)
        report_data = noms.report(meal)
        
        # Format and return the report
        nutrient_list = []
        for nutrient in report_data:
            nutrient_list.append({
                "name": nutrient.get('name', 'Unknown'),
                "value": nutrient.get('value', 0),
                "unit": nutrient.get('unit', ''),
                "percent": nutrient.get('percent', 0)
            })
        
        return nutrient_list
    except Exception as e:
        print(f"Error generating nutrient report: {str(e)}")
        return None

# Example usage
if __name__ == "__main__":
    food_name = "Mashed Potato"
    report = get_nutrient_report(food_name)
    
    if report:
        print("\nNutrient Report (per 100g):")
        for nutrient in report:
            percent_str = f"({nutrient['percent']}%)" if nutrient['percent'] != 0 else ""
            print(f"{nutrient['name']}: {nutrient['value']} {nutrient['unit']} {percent_str}")
    else:
        print("Failed to generate nutrient report")
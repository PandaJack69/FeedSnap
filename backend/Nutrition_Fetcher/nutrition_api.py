from fastapi import FastAPI, Query
from fastapi.middleware.cors import CORSMiddleware
from noms_client import get_nutrient_report

app = FastAPI()

# Allow requests from frontend (adjust IP/port if needed)
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],  # Adjust to specific IP if needed
    allow_methods=["*"],
    allow_headers=["*"]
)

@app.get("/nutrients")
def fetch_nutrition(food: str, amount: int = 100):
    report = get_nutrient_report(food, amount)
    if report:
        return {"status": "success", "data": report}
    return {"status": "error", "message": "No nutrient data found"}

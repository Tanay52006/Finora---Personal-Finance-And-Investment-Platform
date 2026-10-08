# FINORA — Final Full-Stack Finance & Investment Platform

Finora is a premium dark fintech portfolio project with:
- required login before dashboard access
- responsive React + TypeScript UI
- simulated investment workflow
- market search and company detail pages
- portfolio and watchlist
- personal finance
- monthly income creation
- add/edit/delete transactions
- financial goals with progress
- budgets
- notifications
- settings
- Spring Boot REST API
- PostgreSQL persistence for core investment data

## Stack
Frontend: React, TypeScript, Vite, Tailwind CSS, Framer Motion, Recharts, Lucide
Backend: Java 17, Spring Boot, Spring Data JPA, PostgreSQL
Investment behavior: simulated only; no real-money trading.

## 1. PostgreSQL
Create a database:

```sql
CREATE DATABASE finora;
```

Default backend connection:
host localhost
port 5432
database finora
user postgres
password postgres

Change these in:
backend/src/main/resources/application.properties

or set:
DB_URL
DB_USER
DB_PASSWORD

## 2. Backend
Open a terminal:

```bash
cd backend
mvn spring-boot:run
```

Backend: http://localhost:8080

## 3. Frontend
Open a second terminal:

```bash
cd frontend
npm install
npm run dev
```

Open the URL shown by Vite, normally:
http://localhost:5173

## Login
Use:
Email: tanay@finora.demo
Password: demo123

You can also create a new demo account from Register. Authentication is implemented as local application state for this portfolio build; core finance/investment data is API-ready.

## Important
All displayed buttons are intended to work:
- navigation
- search
- login/register/logout
- market filters
- stock details
- watchlist
- invest
- add/edit/delete transaction
- add income
- add/edit/delete goal
- add/edit/delete budget
- notification read/clear
- settings
- theme toggle
- quick actions
- modal close/cancel
- chart range controls

The app uses the browser's current date/time for displayed day/date rather than hardcoding an old date.


## Market data upgrade
Finora now requests market quotes and historical chart data through the Spring Boot backend.
- Indian symbols are mapped to Yahoo Finance NSE symbols (`.NS`).
- AAPL and NVDA use their US symbols.
- Company charts support 1D, 1W, 1M, 6M, 1Y and 5Y ranges.
- Portfolio value/P&L is calculated from holdings and market prices.
- USD holdings are converted to INR using the live USD/INR quote.
- The frontend refreshes live market data approximately every 60 seconds while logged in.
- Investment actions remain paper-trading simulations; no real trades are placed.
- Public market-data endpoints can be delayed or temporarily unavailable, so the backend has a fallback to the last stored/demo value.

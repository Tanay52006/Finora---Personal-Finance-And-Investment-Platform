const API = import.meta.env.VITE_API_URL || "http://localhost:8080/api";

async function request(path:string, options:RequestInit={}) {
  const r=await fetch(`${API}${path}`,{headers:{"Content-Type":"application/json",...(options.headers||{})},...options});
  if(!r.ok) throw new Error(await r.text() || "Request failed");
  if(r.status===204) return null;
  return r.json();
}
export const getStocks=(q="")=>request(`/stocks${q?`?q=${encodeURIComponent(q)}`:""}`);
export const invest=(email:string,symbol:string,amount:number)=>request(`/invest?email=${encodeURIComponent(email)}&symbol=${symbol}&amount=${amount}`,{method:"POST"});
export const getPortfolio=(email:string)=>request(`/portfolio/${encodeURIComponent(email)}`);
export const getFinance=(email:string)=>request(`/finance/transactions/${encodeURIComponent(email)}`);
export const addFinance=(email:string,data:any)=>request(`/finance/transactions/${encodeURIComponent(email)}`,{method:"POST",body:JSON.stringify(data)});
export const updateFinance=(email:string,id:number,data:any)=>request(`/finance/transactions/${encodeURIComponent(email)}/${id}`,{method:"PUT",body:JSON.stringify(data)});
export const deleteFinance=(email:string,id:number)=>request(`/finance/transactions/${encodeURIComponent(email)}/${id}`,{method:"DELETE"});
export const getGoals=(email:string)=>request(`/goals/${encodeURIComponent(email)}`);
export const addGoal=(email:string,data:any)=>request(`/goals/${encodeURIComponent(email)}`,{method:"POST",body:JSON.stringify(data)});
export const updateGoal=(email:string,id:number,data:any)=>request(`/goals/${encodeURIComponent(email)}/${id}`,{method:"PUT",body:JSON.stringify(data)});
export const deleteGoal=(email:string,id:number)=>request(`/goals/${encodeURIComponent(email)}/${id}`,{method:"DELETE"});

export const getStockHistory=(symbol:string,range:string)=>request(`/stocks/${encodeURIComponent(symbol)}/history?range=${encodeURIComponent(range)}`);
export const getStockQuote=(symbol:string)=>request(`/stocks/${encodeURIComponent(symbol)}/quote`);
export const getUsdInr=()=>request(`/stocks/fx/usdinr`);
export const getPortfolioHistory=(email:string,range:string)=>request(`/portfolio/${encodeURIComponent(email)}/history?range=${encodeURIComponent(range)}`);

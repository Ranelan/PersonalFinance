/* Transaction.java
   Transaction POJO class with relationships
   Author: Lebuhang Nyanyantsi(222184353)
   Date: 11 May 2025
*/

package za.ac.cput.domain;
import React, { useState, useEffect } from "react";
import "./App.css";

const API_BASE = "http://localhost:8081/api/transaction";

function TransactionPage() {
  const [transactions, setTransactions] = useState([]);
  const [categories, setCategories] = useState([]);
  const [showForm, setShowForm] = useState(false);
  const [formData, setFormData] = useState({
    amount: "",
    description: "",
    categoryId: "",
    type: "Expense",
    date: new Date().toISOString().split("T")[0],
  });
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  useEffect(() => {
    fetchTransactions();
    fetchCategories();
  }, []);

  const fetchTransactions = async () => {
    try {
      setLoading(true);
      const response = await fetch(`${API_BASE}/findAll`);
      if (response.ok) {
        const data = await response.json();
        setTransactions(data);
      } else {
        setError("Failed to fetch transactions");
      }
    } catch (err) {
      setError("Failed to fetch transactions");
    } finally {
      setLoading(false);
    }
  };

  const fetchCategories = async () => {
    try {
      const response = await fetch("http://localhost:8081/api/category/findAll");
      if (response.ok) {
        const data = await response.json();
        setCategories(data);
      }
    } catch (err) {
      console.error("Failed to fetch categories");
    }
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await fetch(`${API_BASE}/create`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(formData),
      });
      if (response.ok) {
        setShowForm(false);
        setFormData({
          amount: "",
          description: "",
          categoryId: "",
          type: "Expense",
          date: new Date().toISOString().split("T")[0],
        });
        fetchTransactions();
      } else {
        setError("Failed to create transaction");
      }
    } catch (err) {
      setError("Error creating transaction");
    }
  };

  const formatDate = (dateString) =>
    new Date(dateString).toLocaleDateString("en-US", { day: "2-digit", month: "short", year: "numeric" });

  const formatAmount = (amount, type) =>
    type === "Income" ? `R ${amount}` : `-R ${amount}`;

  const getCategoryName = (categoryId) => {
    const category = categories.find((cat) => cat.categoryId === categoryId);
    return category ? category.name : "Uncategorized";
  };

  return (
    <div className="transaction-page">
      <div className="transaction-header">
        <h1>💳 Transactions</h1>
        <button className="btn-add" onClick={() => setShowForm(true)}>+ Add Transaction</button>
      </div>

      {showForm && (
        <div className="modal-overlay">
          <div className="transaction-form">
            <h2>Add New Transaction</h2>
            <form onSubmit={handleSubmit}>
              <div className="form-row">
                <input
                  type="number"
                  name="amount"
                  placeholder="Amount"
                  value={formData.amount}
                  onChange={handleInputChange}
                  required
                />
                <select name="type" value={formData.type} onChange={handleInputChange}>
                  <option value="Expense">Expense</option>
                  <option value="Income">Income</option>
                </select>
              </div>
              <input
                type="text"
                name="description"
                placeholder="Description"
                value={formData.description}
                onChange={handleInputChange}
                required
              />
              <select name="categoryId" value={formData.categoryId} onChange={handleInputChange} required>
                <option value="">Select Category</option>
                {categories.map((cat) => (
                  <option key={cat.categoryId} value={cat.categoryId}>{cat.name}</option>
                ))}
              </select>
              <input type="date" name="date" value={formData.date} onChange={handleInputChange} required />
              <div className="form-actions">
                <button type="button" className="btn-cancel" onClick={() => setShowForm(false)}>Cancel</button>
                <button type="submit" className="btn-submit">Add</button>
              </div>
            </form>
          </div>
        </div>
      )}

      {loading && <div className="loading">Loading...</div>}
      {error && <div className="error">{error}</div>}

      <div className="transaction-list">
        {transactions.length === 0 && !loading && !error ? (
          <div className="no-transactions">No transactions yet.</div>
        ) : (
          transactions.map((t) => (
            <div key={t.id} className={`transaction-card ${t.type.toLowerCase()}`}>
              <div className="transaction-left">
                <div className="transaction-desc">{t.description}</div>
                <div className="transaction-category">{getCategoryName(t.categoryId)}</div>
              </div>
              <div className="transaction-right">
                <div className="transaction-date">{formatDate(t.date)}</div>
                <div className={`transaction-amount ${t.type.toLowerCase()}`}>{formatAmount(t.amount, t.type)}</div>


              </div>
            </div>
          ))
        )}
      </div>
    </div>
  );
}

export default TransactionPage;

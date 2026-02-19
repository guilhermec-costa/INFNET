import React, { useState, useEffect } from 'react';
import ItemList from './components/ItemList';
import ItemForm from './components/ItemForm';
import ItemModal from './components/ItemModal';
import './App.css';

const API_URL = 'http://localhost:8080/api/items';

function App() {
  const [items, setItems] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [showForm, setShowForm] = useState(false);
  const [editingItem, setEditingItem] = useState(null);
  const [deleteConfirm, setDeleteConfirm] = useState(null);

  useEffect(() => {
    fetchItems();
  }, []);

  const fetchItems = async () => {
    try {
      setLoading(true);
      const response = await fetch(API_URL);
      if (!response.ok) {
        throw new Error(`Error: ${response.status} ${response.statusText}`);
      }
      const data = await response.json();
      setItems(data);
      setError(null);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  const handleCreate = async (itemData) => {
    try {
      const response = await fetch(API_URL, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(itemData)
      });
      
      if (!response.ok) {
        const errorData = await response.json();
        throw new Error(errorData.error || 'Failed to create item');
      }
      
      await fetchItems();
      setShowForm(false);
    } catch (err) {
      alert(err.message);
    }
  };

  const handleUpdate = async (id, itemData) => {
    try {
      const response = await fetch(`${API_URL}/${id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(itemData)
      });
      
      if (!response.ok) {
        const errorData = await response.json();
        throw new Error(errorData.error || 'Failed to update item');
      }
      
      await fetchItems();
      setEditingItem(null);
    } catch (err) {
      alert(err.message);
    }
  };

  const handleDelete = async (id) => {
    try {
      const response = await fetch(`${API_URL}/${id}`, {
        method: 'DELETE'
      });
      
      if (!response.ok) {
        const errorData = await response.json();
        throw new Error(errorData.error || 'Failed to delete item');
      }
      
      await fetchItems();
      setDeleteConfirm(null);
    } catch (err) {
      alert(err.message);
    }
  };

  return (
    <div className="app">
      <header className="header">
        <h1>CRUD System</h1>
        <button className="btn-primary" onClick={() => setShowForm(true)}>
          + New Item
        </button>
      </header>

      {error && (
        <div className="error-banner">
          <span>Error: {error}</span>
          <button onClick={() => setError(null)}>×</button>
        </div>
      )}

      {loading ? (
        <div className="loading">Loading...</div>
      ) : (
        <ItemList
          items={items}
          onEdit={setEditingItem}
          onDelete={setDeleteConfirm}
        />
      )}

      {showForm && (
        <ItemModal title="Create Item" onClose={() => setShowForm(false)}>
          <ItemForm onSubmit={handleCreate} onCancel={() => setShowForm(false)} />
        </ItemModal>
      )}

      {editingItem && (
        <ItemModal title="Edit Item" onClose={() => setEditingItem(null)}>
          <ItemForm
            item={editingItem}
            onSubmit={(data) => handleUpdate(editingItem.id, data)}
            onCancel={() => setEditingItem(null)}
          />
        </ItemModal>
      )}

      {deleteConfirm && (
        <div className="modal-overlay">
          <div className="modal">
            <h3>Confirm Delete</h3>
            <p>Are you sure you want to delete "{deleteConfirm.name}"?</p>
            <div className="modal-actions">
              <button className="btn-danger" onClick={() => handleDelete(deleteConfirm.id)}>
                Delete
              </button>
              <button className="btn-secondary" onClick={() => setDeleteConfirm(null)}>
                Cancel
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}

export default App;

import pytest
from unittest.mock import patch
# --- CORRECCIÓN AQUÍ ---
# Como app.py está dentro de la carpeta 'backend', debemos importarlo así:
from backend.app import app 

# --- CONFIGURACIÓN ---
@pytest.fixture
def client():
    app.config['TESTING'] = True
    with app.test_client() as client:
        yield client

# --- TEST 1: GET Todos ---
# Nota: La ruta del patch 'backend.cliente_bp...' ES CORRECTA porque tu carpeta se llama backend.
@patch('backend.cliente_bp.get_clientes') 
def test_get_all(mock_db, client):
    mock_db.return_value = [{"id": 1, "nombre": "Ferreteria Test", "email": "test@ferre.com"}]

    # IMPORTANTE: Revisa si en tu app.py el prefijo es /clientes o /api
    response = client.get('/clientes') 

    assert response.status_code == 200
    assert response.get_json()[0]['nombre'] == "Ferreteria Test"

# --- TEST 2: GET Uno ---
@patch('backend.cliente_bp.get_cliente')
def test_get_one(mock_db, client):
    mock_db.return_value = {"id": 1, "nombre": "Cliente Uno"}
    response = client.get('/clientes/1')
    assert response.status_code == 200
    assert response.get_json()['nombre'] == "Cliente Uno"

# --- TEST 3: GET Uno NO ENCONTRADO ---
@patch('backend.cliente_bp.get_cliente')
def test_get_one_404(mock_db, client):
    mock_db.return_value = None
    response = client.get('/clientes/999')
    assert response.status_code == 404
    assert "error" in response.get_json()

# --- TEST 4: POST Crear ---
@patch('backend.cliente_bp.create_cliente')
def test_create(mock_db, client):
    mock_db.return_value = 50
    nuevo = {"nombre": "Nueva Sucursal", "email": "nueva@sucursal.com"}
    response = client.post('/clientes', json=nuevo)
    assert response.status_code == 201
    assert response.get_json()['id_cliente'] == 50

# --- TEST 5: POST Error Validacion ---
def test_create_error(client):
    mal_dato = {"email": "sin@nombre.com"}
    response = client.post('/clientes', json=mal_dato)
    assert response.status_code == 400
    assert "error" in response.get_json()

# --- TEST 6: PUT Actualizar ---
@patch('backend.cliente_bp.update_cliente')
def test_update(mock_db, client):
    mock_db.return_value = 1
    update_data = {"nombre": "Nombre Actualizado"}
    response = client.put('/clientes/1', json=update_data)
    assert response.status_code == 200
    assert "updated" in response.get_json()['message']

# --- TEST 7: DELETE Borrar ---
@patch('backend.cliente_bp.delete_cliente')
def test_delete(mock_db, client):
    mock_db.return_value = 1
    response = client.delete('/clientes/1')
    assert response.status_code == 200
    assert "deleted" in response.get_json()['message']

# --- TEST 8: GET CDMX ---
@patch('backend.cliente_bp.get_clientes_cdmexico')
def test_get_cdmexico(mock_db, client):
    mock_db.return_value = [{"id": 10, "nombre": "Sucursal Centro", "ciudad": "CDMX"}]
    response = client.get('/clientes/cdmexico')
    assert response.status_code == 200
    assert response.get_json()[0]['ciudad'] == "CDMX"
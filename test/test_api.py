import pytest
from unittest.mock import patch
# Asegúrate de que 'app' se importa correctamente desde tu archivo principal
from app import app 

# --- CONFIGURACIÓN ---
@pytest.fixture
def client():
    app.config['TESTING'] = True
    with app.test_client() as client:
        yield client

# --- TEST 1: GET Todos (Ruta: /clientes) ---
# Apuntamos a donde se USA la función: backend -> cliente_bp -> get_clientes
@patch('backend.cliente_bp.get_clientes') 
def test_get_all(mock_db, client):
    # 1. Simulamos la respuesta de la BD
    mock_db.return_value = [
        {"id": 1, "nombre": "Ferreteria Test", "email": "test@ferre.com"}
    ]

    # 2. Hacemos la petición (Asumiendo prefijo '/clientes' en app.py)
    response = client.get('/clientes') 

    # 3. Verificamos
    assert response.status_code == 200
    assert response.get_json()[0]['nombre'] == "Ferreteria Test"

# --- TEST 2: GET Uno (Ruta: /clientes/1) ---
@patch('backend.cliente_bp.get_cliente')
def test_get_one(mock_db, client):
    # Simulamos encontrar al cliente ID 1
    mock_db.return_value = {"id": 1, "nombre": "Cliente Uno"}

    response = client.get('/clientes/1')

    assert response.status_code == 200
    assert response.get_json()['nombre'] == "Cliente Uno"

# --- TEST 3: GET Uno NO ENCONTRADO ---
@patch('backend.cliente_bp.get_cliente')
def test_get_one_404(mock_db, client):
    # Simulamos que la BD devuelve None (vacío)
    mock_db.return_value = None

    response = client.get('/clientes/999')

    assert response.status_code == 404
    assert "error" in response.get_json()

# --- TEST 4: POST Crear (Ruta: /clientes) ---
@patch('backend.cliente_bp.create_cliente')
def test_create(mock_db, client):
    # Simulamos que la BD crea y devuelve el nuevo ID (ej. 50)
    mock_db.return_value = 50

    nuevo = {"nombre": "Nueva Sucursal", "email": "nueva@sucursal.com"}
    
    response = client.post('/clientes', json=nuevo)

    assert response.status_code == 201
    assert response.get_json()['id_cliente'] == 50

# --- TEST 5: POST Error Validacion (Falta nombre) ---
def test_create_error(client):
    # Enviamos JSON sin la clave 'nombre'
    mal_dato = {"email": "sin@nombre.com"}

    response = client.post('/clientes', json=mal_dato)

    assert response.status_code == 400
    assert "error" in response.get_json()

# --- TEST 6: PUT Actualizar (Ruta: /clientes/1) ---
@patch('backend.cliente_bp.update_cliente')
def test_update(mock_db, client):
    # Simulamos que 1 fila fue afectada (éxito)
    mock_db.return_value = 1

    update_data = {"nombre": "Nombre Actualizado"}
    response = client.put('/clientes/1', json=update_data)

    assert response.status_code == 200
    assert "updated" in response.get_json()['message']

# --- TEST 7: DELETE Borrar (Ruta: /clientes/1) ---
@patch('backend.cliente_bp.delete_cliente')
def test_delete(mock_db, client):
    # Simulamos que 1 fila fue borrada
    mock_db.return_value = 1

    response = client.delete('/clientes/1')

    assert response.status_code == 200
    assert "deleted" in response.get_json()['message']

# --- TEST 8: GET CDMX (Ruta: /clientes/cdmexico) ---
@patch('backend.cliente_bp.get_clientes_cdmexico')
def test_get_cdmexico(mock_db, client):
    mock_db.return_value = [{"id": 10, "nombre": "Sucursal Centro", "ciudad": "CDMX"}]

    response = client.get('/clientes/cdmexico')

    assert response.status_code == 200
    assert response.get_json()[0]['ciudad'] == "CDMX"
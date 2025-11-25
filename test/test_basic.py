# tests/test_basic.py

def test_ejemplo_basico():
    """Prueba simple para verificar que CI funciona"""
    x = 5
    y = 5
    assert x + y == 10

def test_importacion():
    """Verifica que se puede importar flask sin errores"""
    import flask
    assert flask.__version__ is not None
from flask import Flask, jsonify
from flask_jwt_extended import JWTManager
from auth import auth_bp
from cliente_bp import cliente_bp
from producto_bp import producto_bp
from venta_bp import venta_bp
from detalle_venta_bp import detalle_venta_bp
from cuentas_por_cobrar_bp import cuenta_bp
from abono_bp import abono_bp
from compras_producto_bp import compra_bp
from categoria_bp import categoria_bp
from proveedor_bp import proveedor_bp
from tipo_de_pago_bp import tipo_pago_bp

app = Flask(__name__)
app.config['JWT_SECRET_KEY'] = 'your-secret-key'
jwt = JWTManager(app)

# Register blueprints with prefixes
app.register_blueprint(auth_bp, url_prefix='/auth')
app.register_blueprint(cliente_bp, url_prefix='/clientes')
app.register_blueprint(producto_bp, url_prefix='/productos')
app.register_blueprint(venta_bp, url_prefix='/ventas')
app.register_blueprint(detalle_venta_bp, url_prefix='/detalle-ventas')
app.register_blueprint(cuenta_bp, url_prefix='/cuentas-por-cobrar')
app.register_blueprint(abono_bp, url_prefix='/abonos')
app.register_blueprint(compra_bp, url_prefix='/compras-producto')
app.register_blueprint(categoria_bp, url_prefix='/categorias')
app.register_blueprint(proveedor_bp, url_prefix='/proveedores')
app.register_blueprint(tipo_pago_bp, url_prefix='/tipos-pago')

# Error handling
@app.errorhandler(404)
def not_found(error):
    return jsonify({"error": "Resource not found"}), 404

@app.errorhandler(400)
def bad_request(error):
    return jsonify({"error": "Bad request"}), 400

@app.errorhandler(500)
def server_error(error):
    return jsonify({"error": "Internal server error"}), 500

if __name__ == '__main__':
    # '0.0.0.0' le dice a Flask: "Escucha a todo el mundo"
    app.run(host='0.0.0.0', port=5000, debug=True)

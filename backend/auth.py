from flask import Blueprint, request, jsonify
from flask_jwt_extended import (
    create_access_token, jwt_required, get_jwt
)
from db import get_user_by_username, create_user, verify_user

auth_bp = Blueprint('auth', __name__)

@auth_bp.route('/login', methods=['POST'])
def login():
    data = request.get_json()

    if not data or 'username' not in data or 'password' not in data:
        return jsonify({'msg': 'Missing username or password'}), 400

    user = verify_user(data['username'], data['password'])
    if not user:
        return jsonify({'msg': 'Invalid credentials'}), 401

    access_token = create_access_token(
        identity=user['id'],
        additional_claims={'role': user['role']}
    )
    return jsonify(access_token=access_token), 200


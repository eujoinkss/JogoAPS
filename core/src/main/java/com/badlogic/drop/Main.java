package com.badlogic.drop;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main implements ApplicationListener {
    private boolean isOnGround = true;
    private boolean olhandoDireita = true;
    private float velocidadeY = 3f;
    // Instanciando objetos da biblioteca GDX
    private Texture background;
    private Texture prota;
    private Sprite protaSprite;
    private FitViewport viewport;
    private SpriteBatch spriteBatch;
    private OrthographicCamera camera;
    
    @Override
    public void create() {
        // Método para "Construir" o jogo
        // Atribuindo valores aos objetos do tipo "Textura", esses valores são imagem salvas no diretório do projeto
        background = new Texture("fundo.png");
        prota = new Texture("Valdomir0.png");
        
        spriteBatch = new SpriteBatch();
        // O objeto "OrthographicCamera" é usado para criar uma câmera dinâmica que segue o personagem horizontalmente
        camera = new OrthographicCamera();
        // viewport controlará a janela que é usada para visualizarmos e interagirmos com o jogo
        viewport = new FitViewport(100,70, camera);
        
        // Aqui é criado um sprite do protagonista usando a textura anteriormene atribuida, para que ele possa ser controlado
        protaSprite = new Sprite(prota);
        protaSprite.setSize(8, 8);
        protaSprite.setPosition(0, 0);
    }

    @Override
    public void resize(int width, int height) {
        // If the window is minimized on a desktop (LWJGL3) platform, width and height are 0, which causes problems.
        // In that case, we don't resize anything, and wait for the window to be a normal size before updating.
        if(width <= 0 || height <= 0) return;
        // Aqui garantimos que quando a janela tiver suas dimensões alteradas, ela mudará de tamanho sem perda de qualidade
        viewport.update(width, height, true);
    }

    @Override
    public void render() {
        // Aqui estão os métodos que serão repetidos em cada frame da aplicação
        draw();
        input();
        logic();
    }
    
    private void draw(){
        // Como o fundo se repete três vezes para dar uma sensação de um mapa grande, colocamos que a largura do mundo é o triplo da largura do fundo
        final float largura = background.getWidth() * 3;
        final float altura = viewport.getWorldHeight();
        float cameraX = protaSprite.getX() + protaSprite.getWidth() / 2;
        // método para "desenhar" o mundo, mostrando todos os elementos gráficos
        //Limpando a tela para começar a exibição
        ScreenUtils.clear(Color.BLACK);
        // Aqui setamos a càmera para acompanhar o sprite do jogador e para parar de seguir nos extremos do mapa
        cameraX = Math.max(viewport.getWorldWidth() / 2, cameraX);
        cameraX = Math.min(largura - viewport.getWorldWidth() / 2, cameraX);
        camera.position.x = cameraX;
        camera.update();
        spriteBatch.setProjectionMatrix(camera.combined);
        // Aqui será feita a exibição
        spriteBatch.begin();
        
        spriteBatch.draw(background, 0, 0, background.getWidth(), altura);
        spriteBatch.draw(background, background.getWidth(), 0, background.getWidth(), altura);
        spriteBatch.draw(background, background.getWidth() * 2, 0, background.getWidth(), altura);
        protaSprite.draw(spriteBatch);
        
        spriteBatch.end();
        
    }
    
    private void input(){
        final float chaoY = 0;
        final int gravidade = 100;
        final int forcaPulo = 60;
        float velocidade = 30f;
        final float largura = background.getWidth() * 3;
        
        // DeltaTime = O tempo entre os frames
        float delta = Gdx.graphics.getDeltaTime();
        
        
        // Mover o personagem para a direita
        if(Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            
            protaSprite.translateX(velocidade * delta);
            
            if(protaSprite.getX() > largura - protaSprite.getWidth()){
                protaSprite.setX(largura - protaSprite.getWidth());
            }
            
            if(!olhandoDireita){
                protaSprite.flip(true, false);
                olhandoDireita = true;
            }
          // Mover o personagem para a esquerda
        } else if(Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            
            protaSprite.translateX(-velocidade * delta);
            
            if(protaSprite.getX() < 0){
                protaSprite.setX(0);
            }
            
            if(olhandoDireita){
                protaSprite.flip(true, false);
                olhandoDireita = false;
            }
            
        } 
        
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE) && isOnGround){
            
            velocidadeY = forcaPulo;
            isOnGround = false;
            
        }
        velocidadeY -= gravidade * delta;
        protaSprite.setY(protaSprite.getY() + velocidadeY * delta);
        
        if(protaSprite.getY() <= chaoY){
            
            protaSprite.setY(chaoY);
            velocidadeY = 0;
            isOnGround = true;
        }
        
        System.out.println(delta);
    }
    
    private void logic(){
        
    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void dispose() {
        // Destroy application's resources here.
    }
}
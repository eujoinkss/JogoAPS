package com.badlogic.drop;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main implements ApplicationListener {
    boolean olhandoDireita = true;
    // Instanciando objetos da biblioteca GDX
    Texture background;
    Texture prota;
    Sprite protaSprite;
    FitViewport viewport;
    SpriteBatch spriteBatch;
    
    @Override
    public void create() {
        // Método para "Construir" o jogo
        // Atribuindo valores aos objetos do tipo "Textura", esses valores são imagem salvas no diretório do projeto
        background = new Texture("background_placeholder.png");
        prota = new Texture("Valdomir0.png");
        
        spriteBatch = new SpriteBatch();
        // viewport controlará a janela que é usada para visualizarmos e interagirmos com o jogo
        viewport = new FitViewport(10,7);
        
        // Aqui é criado um sprite do protagonista usando a textura anteriormene atribuida, para que ele possa ser controlado
        protaSprite = new Sprite(prota);
        protaSprite.setSize(1, 1);
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
        // método para "desenhar" o mundo, mostrando todos os elementos gráficos
        //Limpando a tela para começar a exibição
        ScreenUtils.clear(Color.BLACK);
        // Aqui garantimos que tudo seja desenhado na tela exibida para o usuário
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        // Aqui será feita a exibição
        spriteBatch.begin();
        
        float largura = viewport.getWorldWidth();
        float altura = viewport.getWorldHeight();
        
        spriteBatch.draw(background, 0, 0, largura, altura);
        protaSprite.draw(spriteBatch);
        
        spriteBatch.end();
        
    }
    
    private void input(){
        float velocidade = 25f;
        // DeltaTime = O tempo entre os frames
        float delta = Gdx.graphics.getDeltaTime();
        
        
        // Mover o personagem para a direita
        if(Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            
            protaSprite.translateX(velocidade * delta);
            
            if(!olhandoDireita){
                protaSprite.flip(true, false);
                olhandoDireita = true;
            }
          // Mover o personagem para a esquerda
        } else if(Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            
            protaSprite.translateX(-velocidade * delta);
            
            if(olhandoDireita){
                protaSprite.flip(true, false);
                olhandoDireita = false;
            }
            
        }
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
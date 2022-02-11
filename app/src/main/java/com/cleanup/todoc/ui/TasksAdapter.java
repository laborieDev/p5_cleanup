package com.cleanup.todoc.ui;

import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.cleanup.todoc.R;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.util.List;

/**
 * <p>Adapter which handles the list of tasks to display in the dedicated RecyclerView.</p>
 *
 * @author Gaëtan HERFRAY
 */
public class TasksAdapter extends ListAdapter<Task, TaskViewHolder> {

    /**
     * The listener for when a task needs to be deleted
     */
    @NonNull
    private final DeleteTaskListener deleteTaskListener;

    /**
     * Instantiates a new TasksAdapter.
     *
     */
    TasksAdapter(@NonNull final DeleteTaskListener deleteTaskListener) {
        super(DIFF_CALLBACK);
        this.deleteTaskListener = deleteTaskListener;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_task, viewGroup, false);
        return new TaskViewHolder(view, deleteTaskListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder taskViewHolder, int position) {
        taskViewHolder.bind(getItem(position));
    }

    public static final DiffUtil.ItemCallback<Task> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Task>() {
                @Override
                public boolean areItemsTheSame(
                        @NonNull Task oldTask, @NonNull Task newTask) {
                    // User properties may have changed if reloaded from the DB, but ID is fixed
                    return oldTask.getId() == newTask.getId();
                }
                @Override
                public boolean areContentsTheSame(
                        @NonNull Task oldTask, @NonNull Task newTask) {
                    // NOTE: if you use equals, your object must properly override Object#equals()
                    // Incorrectly returning false here will result in too many animations.
                    return oldTask.equals(newTask);
                }
            };

    /**
     * Listener for deleting tasks
     */
    public interface DeleteTaskListener {
        /**
         * Called when a task needs to be deleted.
         *
         * @param task the task that needs to be deleted
         */
        void onDeleteTask(Task task);
    }
}

/**
 * <p>ViewHolder for task items in the tasks list</p>
 *
 * @author Gaëtan HERFRAY
 */
class TaskViewHolder extends RecyclerView.ViewHolder {
    /**
     * The circle icon showing the color of the project
     */
    private final AppCompatImageView imgProject;

    /**
     * The TextView displaying the name of the task
     */
    private final TextView lblTaskName;

    /**
     * The TextView displaying the name of the project
     */
    private final TextView lblProjectName;

    /**
     * The delete icon
     */
    private final AppCompatImageView imgDelete;

    /**
     * The listener for when a task needs to be deleted
     */
    private final TasksAdapter.DeleteTaskListener deleteTaskListener;

    /**
     * Instantiates a new TaskViewHolder.
     *
     * @param itemView the view of the task item
     * @param deleteTaskListener the listener for when a task needs to be deleted to set
     */
    TaskViewHolder(@NonNull View itemView, @NonNull TasksAdapter.DeleteTaskListener deleteTaskListener) {
        super(itemView);

        this.deleteTaskListener = deleteTaskListener;

        imgProject = itemView.findViewById(R.id.img_project);
        lblTaskName = itemView.findViewById(R.id.lbl_task_name);
        lblProjectName = itemView.findViewById(R.id.lbl_project_name);
        imgDelete = itemView.findViewById(R.id.img_delete);

        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Object tag = view.getTag();
                if (tag instanceof Task) {
                    TaskViewHolder.this.deleteTaskListener.onDeleteTask((Task) tag);
                }
            }
        });
    }

    /**
     * Binds a task to the item view.
     *
     * @param task the task to bind in the item view
     */
    void bind(Task task) {
        lblTaskName.setText(task.getName());
        imgDelete.setTag(task);

        final Project taskProject = task.getProject();
        if (taskProject != null) {
            imgProject.setSupportImageTintList(ColorStateList.valueOf(taskProject.getColor()));
            lblProjectName.setText(taskProject.getName());
        } else {
            imgProject.setVisibility(View.INVISIBLE);
            lblProjectName.setText("");
        }

    }
}
